#version 330 core
out vec4 FragColor;
uniform vec3 objectColor;
uniform vec3 lightColor;
void main()
{
    float ambientStrengh = 0.1;
    FragColor = vec4(ambientStrengh * lightColor * objectColor, 1.0);
}
