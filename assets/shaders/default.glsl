#type vertex
#version 330 core

layout (location=0) in vec2 aPos;

uniform mat4 uProjMat;
uniform mat4 uViewMat;

out vec4 fColor;

void main()
{
    fColor = vec4(1.0, 0.0, 0.0, 1.0);
    gl_Position = uProjMat * uViewMat * vec4(aPos, 0.0, 1.0);
}


#type fragment
#version 330 core

in vec4 fColor;
out vec4 color;

uniform float alpha;

void main()
{
    color = fColor - alpha;
}