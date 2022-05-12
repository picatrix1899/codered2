#version 400

in vec3 coords;
in vec2 texCoords;

out vec2 pass_texCoords;

uniform mat4 T_projection;
uniform mat4 T_model;
uniform mat4 T_view;

void main()
{
	gl_Position = T_projection * T_view * T_model * vec4(coords, 1.0);
	
	pass_texCoords = texCoords;
}