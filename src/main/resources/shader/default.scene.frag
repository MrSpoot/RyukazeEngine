#version 330 core

uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 objectColor;

in vec3 FragPos;
in vec3 Normal;

out vec4 FragColor;

void main()
{
    float strengh = 0.5;
    vec3 ambient = strengh * lightColor;

    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPos - FragPos);

    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    vec3 result = (ambient + diffuse) * objectColor;
    FragColor = vec4(result, 1.0);
}
