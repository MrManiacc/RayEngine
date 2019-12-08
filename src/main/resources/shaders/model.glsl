#vert
#version 330 core
in vec3 vertex;
in vec2 texCoords;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectMatrix;

void main()
{
    gl_Position = projectMatrix * viewMatrix * modelMatrix * vec4(vertex, 1.0);
}

    #frag
    #version 330 core
out vec4 outColor;

void main()
{
    outColor = vec4(1, 0, 0, 1.0);
}





