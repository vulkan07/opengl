#type vertex
#version 330 core

layout (location=0) in vec2 aPos;
layout (location=1) in vec2 aUV;

uniform mat4 uProjMat;
uniform mat4 uViewMat;

out vec2 fUV;
out vec2 fPos;

void main()
{
    fUV = aUV;
    gl_Position = uProjMat * uViewMat * vec4(aPos, 0.0, 1.0);
    fPos = gl_Position.xy;
}




#type fragment
#version 330 core

in vec2 fUV;
in vec2 fPos;
out vec4 color;

uniform float uAlpha;
uniform sampler2D uTexSampler;
uniform sampler2D uNorSampler;

vec2 lightPos = vec2(.6);

float remap(float value, float low1, float high1, float low2, float high2) {
    return low2 + (value - low1) * (high2 - low2) / (high1 - low1);
}

void main()
{

    //Specify brightnes based on the normalmap value and the lights pos
    vec2 norVec = texture(uNorSampler, fUV).xy*2;
    float brightness = min(.55, distance(fPos+norVec, lightPos)/4.);

    vec4 tColor = texture(uTexSampler, fUV);
    if (tColor.a == 0.)
        discard;

    tColor.xyz -= brightness;

    color = tColor-uAlpha;
}

