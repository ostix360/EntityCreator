#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;

uniform vec3 lightColor;
uniform vec3 attenuation;
uniform float lightPower;
uniform float shine;
uniform float reflectivity;
uniform vec3 skyColor;

const int pcfCount = 2;
const float totalsTexels = (pcfCount * 2 - 1) * (pcfCount * 2 - 1);

void main() {

//    float shadowMapSize = 8192;
//    float texelSize = 1/shadowMapSize;
//    float total = 0.0;
//
//    for (int x =-pcfCount; x <= pcfCount; x++){
//        for (int y =-pcfCount; y <= pcfCount; y++){
//            float objectNearstLght = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
//            if (shadowCoords.z > objectNearstLght){
//                total += 1.0;
//            }
//        }
//    }
//
//    total /= totalsTexels;
//
//    float lightFactor = 0.7 -  (clamp(total, 0.0, 0.4) * shadowCoords.w);


    vec4 blendMapColour = texture(blendMap, pass_textureCoords);

    float backTextureAmount = 1 - (blendMapColour.r, blendMapColour.g, blendMapColour.b);
    vec2 tiledCoords = pass_textureCoords *40;
    vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;

    vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;


    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpeculare= vec3(0.0);


        float distance = length(toLightVector);
        float attenuationFactor = max(attenuation.x + (attenuation.y * distance) + (attenuation.z * distance * distance), 1.0);
        vec3 unitLightVector = normalize(toLightVector);

        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);

        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shine);
        totalDiffuse = totalDiffuse + (brightness * lightColor * lightPower)/attenuationFactor;
        totalSpeculare = totalSpeculare + max(vec3(0.), (dampedFactor * lightColor* reflectivity))/attenuationFactor;

    totalDiffuse = clamp(totalDiffuse,0.2,0.9);
    //totalDiffuse = max(totalDiffuse * lightFactor, 0.001);


    out_Color = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpeculare, 1.0);
    out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
    //out_BrightColor = vec4(0.0);
}
