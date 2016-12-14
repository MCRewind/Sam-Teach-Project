#version 330

uniform sampler2D sampler;

in vec2 tex;

layout (location = 0) out vec4 color;

void main() {
	color = texture(sampler, tex);
}