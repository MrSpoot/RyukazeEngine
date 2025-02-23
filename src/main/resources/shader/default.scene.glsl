@vs
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aTexCoords;

layout (location = 3) in vec4 modelMatrixCol0;
layout (location = 4) in vec4 modelMatrixCol1;
layout (location = 5) in vec4 modelMatrixCol2;
layout (location = 6) in vec4 modelMatrixCol3;

layout (location = 7) in vec3 normalMatrixCol0;
layout (location = 8) in vec3 normalMatrixCol1;
layout (location = 9) in vec3 normalMatrixCol2;

uniform mat4 view;
uniform mat4 projection;

out vec3 FragPos;
out vec3 Normal;
out vec2 TexCoords;

void main(){

    mat4 model = mat4(modelMatrixCol0, modelMatrixCol1, modelMatrixCol2, modelMatrixCol3);
    mat3 normalMatrix = mat3(normalMatrixCol0, normalMatrixCol1, normalMatrixCol2);

    gl_Position = projection * view * model * vec4(aPos, 1.0);
    FragPos = vec3(model * vec4(aPos, 1.0));
    Normal = normalize(normalMatrix * aNormal);
    TexCoords = aTexCoords;
}
@endvs

@fs
#version 330 core

struct Material {
    sampler2D diffuse;
    sampler2D specular;
    float shininess;
};

struct DirectionalLight {
    vec3 direction;
    float intensity;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct SpotLight {
    vec3 position;
    vec3 direction;
    float cutOff;
    float outerCutOff;
    float intensity;

    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct PointLight {
    vec3 position;
    float intensity;

    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform DirectionalLight directionalLight;
uniform PointLight pointLight;
uniform SpotLight spotLight;
uniform Material material;

uniform vec3 viewPos;

in vec3 FragPos;
in vec3 Normal;
in vec2 TexCoords;

out vec4 FragColor;

vec4 CalcDirLight(DirectionalLight light, vec3 normal, vec3 viewDir);
vec4 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir);
vec4 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDir);

void main()
{
    vec3 norm = normalize(Normal);
    vec3 viewDir = normalize(viewPos - FragPos);

    vec4 texture = texture(material.diffuse,TexCoords);

    if(texture.a < 0.1){
        discard;
    }

    if(texture.a > 0.1 && texture.a < 0.9){
        FragColor = texture;
    }

    if(texture.a > 0.9){
        vec4 result = vec4(0.1,0.1,0.1,1.0);

        result += CalcDirLight(directionalLight,norm,viewDir);
        result += CalcPointLight(pointLight,norm,FragPos,viewDir);
        result += CalcSpotLight(spotLight,norm,FragPos,viewDir);

        FragColor = result;
    }
}

vec4 CalcDirLight(DirectionalLight light, vec3 normal, vec3 viewDir){
    vec3 lightDir = normalize(-light.direction);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    //vec3 reflectDir = reflect(-lightDir, normal);
    //float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // combine results
    vec4 ambient = vec4(light.ambient,1.0) * vec4(texture(material.diffuse, TexCoords));
    vec4 diffuse = vec4(light.diffuse,1.0) * diff * vec4(texture(material.diffuse, TexCoords));
    //vec4 specular = vec4(light.specular,1.0) * spec * vec4(texture(material.specular, TexCoords));

    ambient *= light.intensity;
    diffuse *= light.intensity;
    //specular *= light.intensity;

    return (ambient + diffuse);
}

vec4 CalcSpotLight(SpotLight light, vec3 normal,vec3 fragPos, vec3 viewDir){
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // attenuation
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (1.0 + light.linear * distance + light.quadratic * (distance * distance));
    // spotlight intensity
    float theta = dot(lightDir, normalize(-light.direction));
    float epsilon = light.cutOff - light.outerCutOff;
    float _intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);
    // combine results
    vec4 ambient = vec4(light.ambient,1.0) * vec4(texture(material.diffuse, TexCoords));
    vec4 diffuse = vec4(light.diffuse,1.0) * diff * vec4(texture(material.diffuse, TexCoords));
    vec4 specular = vec4(light.specular,1.0) * spec * vec4(texture(material.specular, TexCoords));
    ambient *= attenuation * _intensity * light.intensity;
    diffuse *= attenuation * _intensity * light.intensity;
    specular *= attenuation * _intensity * light.intensity;
    return (ambient + diffuse + specular);
}

vec4 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir){
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // attenuation
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (1.0 + light.linear * distance + light.quadratic * (distance * distance));
    // combine results
    vec4 ambient = vec4(light.ambient,1.0) * vec4(texture(material.diffuse, TexCoords));
    vec4 diffuse = vec4(light.diffuse,1.0) * diff * vec4(texture(material.diffuse, TexCoords));
    vec4 specular = vec4(light.specular,1.0) * spec * vec4(texture(material.specular, TexCoords));

    ambient *= attenuation * light.intensity;
    diffuse *= attenuation * light.intensity;
    specular *= attenuation * light.intensity;

    return (ambient + diffuse + specular);
}
@endfs