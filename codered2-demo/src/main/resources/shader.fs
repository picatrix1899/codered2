#version 400

in vec2 pass_texCoords;

layout(location=0) out vec4 out_Color;

uniform sampler2D diffuse;
uniform sampler2DArray test;

void main()
{
	out_Color = texture(diffuse, pass_texCoords);
}