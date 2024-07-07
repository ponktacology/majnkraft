#version 400 core

in vec3 position;
in vec2 texturePos;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

out vec2 pass_texturePos;

void main() {
    gl_Position = projectionMatrix * transformationMatrix * vec4(position.x + 0.5, position.y + 0.5, position.z, 1.0);
    pass_texturePos = texturePos;
}