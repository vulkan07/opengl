#type vertex
#version 330 core

layout (location=0) in vec2 aPos;
layout (location=1) in vec2 aUV;

uniform mat4 uProjMat;
uniform mat4 uViewMat;

out vec2 fUV;
out vec2 fPos;
out vec2 lightPos;

void main()
{
    fUV = aUV;
    gl_Position = uProjMat * uViewMat * vec4(aPos, 0.0, 1.0);
    fPos = gl_Position.xy;
    lightPos = (uProjMat * uViewMat * vec4(960,540, 0.0, 1.0)).xy;
}




#type fragment
#version 330 core

in vec2 fUV;
out vec4 color;
in vec2 fPos;

uniform float uAlpha;

uniform sampler2D uTexSampler;
uniform sampler2D uNorSampler;

uniform vec4 uBackColor;
vec4 lightCol = vec4(.8,.5,.2,0.);
in vec2 lightPos;


void main()
{
    //Specify brightnes based on the normalmap value and the lights pos
    vec2 norVec = texture(uNorSampler, fUV).xy*2;
    float brightness = distance(fPos+norVec, lightPos)*1.5/4.;

    vec4 tColor = texture(uTexSampler, fUV);
    if (tColor.a < .2)
        discard;

    vec4 avg = vec4((tColor.x + tColor.y + tColor.z) / 3) + .1;
    avg.w = 1;
    tColor = mix(avg, tColor, .4);

    tColor.xyz *= clamp((vec4(1)-brightness/lightCol).xyz, .1, 1);

    color = tColor - uBackColor - uAlpha;
}