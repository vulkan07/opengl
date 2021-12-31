#type vertex
#version 330 core

layout (location=0) in vec2 aPos;
layout (location=1) in vec2 aUV;

uniform mat4 uProjMat;
uniform mat4 uViewMat;
uniform float uTileID;

out vec2 fUV;

void main()
{
    fUV = aUV;
    gl_Position = uProjMat * uViewMat * vec4(aPos, 0.0, 1.0);
    //fColor = vec4(uTileID/100);
}


#type fragment
#version 330 core

in vec2 fUV;
out vec4 color;
uniform float uAlpha;
uniform sampler2D uTexSampler;

void main()
{
    color = texture(uTexSampler, fUV); //xyzw
}