#version 200

void main(out vec4 fragColor, in vec2 fragCoord) {
    vec2 q = fragCoord.xy / iResolution.xy;//todo what's iResolution?
    vec3 col = texture(iChannel0, q).rgb;
    col *= sin(gl_FragCoord.y*350.+time)*0.04+1.;//Scanlines
    col *= sin(gl_FragCoord.x*350.+time)*0.04+1.;
    col *= pow(16.0*q.x*q.y*(1.0-q.x)*(1.0-q.y), 0.1)*0.35+0.65;//Vign
    fragColor = vec4(col, 1.0);
}
