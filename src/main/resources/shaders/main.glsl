#vert
#version 330 core
in vec3 vertex;
in vec2 texCoords;
out vec2 outTexCoords;
out vec3 outVertex;

void main()
{
    outTexCoords = texCoords;
    outVertex = vertex;
    gl_Position = vec4(vertex, 1.0);
}

    #frag
    #version 330 core
in vec2 outTexCoords;
out vec4 outColor;
in vec3 outVertex;

void main()
{
    outColor = vec4(outVertex.x + 0.5, outVertex.y + 0.5, 1, 1.0);
}





