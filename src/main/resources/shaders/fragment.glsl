#version 400 core

in vec2 pass_texturePos;
out vec4 output_color;
uniform sampler2D textureSampler;

void main() {
    output_color = texture(textureSampler, pass_texturePos);
}