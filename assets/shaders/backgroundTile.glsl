#type vertex
#version 330 core

layout (location=0) in vec2 aPos;
layout (location=1) in vec2 aUV;

uniform mat4 uProjMat;
uniform mat4 uViewMat;

out vec2 fUV;

void main()
{
    fUV = aUV;
    gl_Position = uProjMat * uViewMat * vec4(aPos, 0.0, 1.0);
}




#type fragment
#version 330 core

in vec2 fUV;
out vec4 color;

uniform float uAlpha;
uniform sampler2D uTexSampler;
uniform vec4 uBackColor;

void main()
{
    vec4 tColor = texture(uTexSampler, fUV);
    if (tColor.a == 0.)
    discard;

    vec4 avg = vec4((tColor.x + tColor.y + tColor.z) / 3) + .1;
    avg.w = 1;
    tColor = mix(avg, tColor, .4);

    color = tColor - uBackColor - uAlpha;
}