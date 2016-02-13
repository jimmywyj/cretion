uniform sampler2D u_texture;

uniform float ResolutionX;
uniform float ResolutionY;

uniform int lightCount;
uniform float lightPositions[20];
uniform float lightColors[40];
uniform float ambientColors[40];
uniform float lightFalloffs[30];
uniform float lightDirections[20];

void main()
{
    int positionIndex = 0;
    int colorIndex = 0;
    int ambientIndex = 0;
    int falloffIndex = 0;
    int directionIndex = 0;

    vec3 FinalColor = vec3(0, 0, 0);
    vec4 DiffuseColor = texture2D(u_texture, gl_TexCoord[0]);
    for (int i = 0; i < lightCount; i++)
    {
        vec2 Resolution = vec2(ResolutionX, ResolutionY);
        vec3 LightPos = vec3(lightPositions[positionIndex], lightPositions[positionIndex + 1], 0.0);
        vec4 LightColor = vec4(lightColors[colorIndex], lightColors[colorIndex + 1], lightColors[colorIndex + 2], lightColors[colorIndex + 3]);
        vec4 AmbientColor = vec4(ambientColors[ambientIndex], ambientColors[ambientIndex + 1], ambientColors[ambientIndex + 2], ambientColors[ambientIndex + 3]);
        vec3 Falloff = vec3(lightFalloffs[falloffIndex], lightFalloffs[falloffIndex + 1], lightFalloffs[falloffIndex + 2]);
        vec3 Direction = vec3(lightDirections[directionIndex], lightDirections[directionIndex + 1], 0.0);

        vec3 NormalMap = texture2D(u_texture, gl_TexCoord[0]).rgb;
        vec3 LightDir = vec3(LightPos.xy - (gl_FragCoord.xy / Resolution.xy), LightPos.z);
        LightDir.x *= Resolution.x / Resolution.y;
        float D = length(LightDir);

        vec3 N = normalize(NormalMap * 2.0 - 1.0);
        vec3 L = normalize(LightDir) * normalize(Direction);

        vec3 Diffuse = (LightColor.rgb * LightColor.a) * max(dot(N, L), 0.0);
        vec3 Ambient = AmbientColor.rgb * AmbientColor.a;
        float Attenuation = 1.0 / ( Falloff.x + (Falloff.y*D) + (Falloff.z*D*D) );
        vec3 Intensity = Ambient + Diffuse * Attenuation;
        FinalColor = FinalColor + (DiffuseColor.rgb * Intensity);

        positionIndex += 2;
        colorIndex += 4;
        ambientIndex += 4;
        falloffIndex += 3;
        directionIndex += 2;
    }
    gl_FragColor = vec4(FinalColor, DiffuseColor.a);
}