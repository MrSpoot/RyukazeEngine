#version 330 core

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
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

out vec4 FragColor;

vec3 CalcDirLight(DirectionalLight light, vec3 normal, vec3 viewDir);
vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir);
vec3 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDir);

void main()
{
    vec3 norm = normalize(Normal);
    vec3 viewDir = normalize(viewPos - FragPos);

    vec3 result = CalcDirLight(directionalLight,norm,viewDir);

    result += CalcPointLight(pointLight,norm,FragPos,viewDir);
    result += CalcSpotLight(spotLight,norm,FragPos,viewDir);

    FragColor = vec4(result, 1.0);
}

vec3 CalcDirLight(DirectionalLight light, vec3 normal, vec3 viewDir){
    vec3 lightDir = normalize(-light.direction);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // combine results
    vec3 ambient = light.ambient * material.ambient;// vec3(texture(material.diffuse, TexCoords));
    vec3 diffuse = light.diffuse * diff * material.diffuse;//vec3(texture(material.diffuse, TexCoords));
    vec3 specular = light.specular * spec * material.specular;//vec3(texture(material.specular, TexCoords));

    ambient *= light.intensity;
    diffuse *= light.intensity;
    specular *= light.intensity;

    return (ambient + diffuse + specular);
}

vec3 CalcSpotLight(SpotLight light, vec3 normal,vec3 fragPos, vec3 viewDir){
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // attenuation
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));
    // spotlight intensity
    float theta = dot(lightDir, normalize(-light.direction));
    float epsilon = light.cutOff - light.outerCutOff;
    float _intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);
    // combine results
    vec3 ambient = light.ambient * material.ambient;// vec3(texture(material.diffuse, TexCoords));
    vec3 diffuse = light.diffuse * diff * material.diffuse;//vec3(texture(material.diffuse, TexCoords));
    vec3 specular = light.specular * spec * material.specular;//vec3(texture(material.specular, TexCoords));
    ambient *= attenuation * _intensity * light.intensity;
    diffuse *= attenuation * _intensity * light.intensity;
    specular *= attenuation * _intensity * light.intensity;
    return (ambient + diffuse + specular);
}

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir){
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // attenuation
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));
    // combine results
    vec3 ambient = light.ambient * material.ambient;// vec3(texture(material.diffuse, TexCoords));
    vec3 diffuse = light.diffuse * diff * material.diffuse;//vec3(texture(material.diffuse, TexCoords));
    vec3 specular = light.specular * spec * material.specular;//vec3(texture(material.specular, TexCoords));

    ambient *= attenuation * light.intensity;
    diffuse *= attenuation * light.intensity;
    specular *= attenuation * light.intensity;

    return (ambient + diffuse + specular);
}
