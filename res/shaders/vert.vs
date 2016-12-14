# version 330

uniform mat4 pr_matrix;
uniform mat4 vw_matrix;

in vec3 verts;
in vec2 texts;

out vec2 tex;

void main() {
	tex = texts;
	gl_Position = pr_matrix * vw_matrix * vec4(verts, 1.0);
}