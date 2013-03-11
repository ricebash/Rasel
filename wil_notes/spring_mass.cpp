#include <GL/glfw.h>
#include <iostream>
#include <stdio.h>

#include "shader_helper.h"
#include "Polygon.h"

using namespace std;

const double SPF = 1.0/60.0;
const double scale = .01;
const double boxSize = 15;

#define MAT_SET_TRANSLATE(M, x, y) { M[6] = (x); M[7] = (y); }

double yPos = 0, xPos = 0;
double xPrevPos = xPos;
double xVel = 0;
bool isDragging = false;

bool useVerlet = true;

const double K = 100, M = 1;

void update(float dt) {
	if ( isDragging ) return;
	double F = -K * xPos;
	double a = F/M;

	if ( useVerlet ) {
		double tmp = xPos;
		xPos = 2 * xPos - xPrevPos + a * dt * dt;
		xPrevPos = xPos;
	} else {
		xVel += a * dt;
		xPos += xVel * dt;
	}
}


void onMouseButton(int button, int action);
void onMouseMove(int x, int y);

int main() {
	glfwInit();

	glfwOpenWindow(300, 300, 8, 8, 8, 8, 0, 0, GLFW_WINDOW);
	glfwSetMouseButtonCallback(onMouseButton);
	glfwSetMousePosCallback(onMouseMove);

	Polygon p(boxSize, 4, 0xFFFF00FF, M_PI * 0.25);

	GLuint mainProgram;
	AttribBinding bindings[] = {
		{"p", Polygon::ATTR_POS},
		{"color", Polygon::ATTR_COLOR}
	};
	loadShaders(mainProgram, "simple.vsh", "simple.fsh", bindings, 2);
	const GLuint U_TRANSFORM = glGetUniformLocation(mainProgram, "transform");
	glUseProgram(mainProgram);

	GLfloat mat[] = {
		scale, 0, 0,
		0, scale, 0,
		0, 0, 1
	};
	
	double t = 0;

	double prev = glfwGetTime();
	double acc = 0;
	double dir = 1;

	
	while ( glfwGetWindowParam( GLFW_OPENED ) && !glfwGetKey(GLFW_KEY_ESC) ) {
		const double cur = glfwGetTime();
		const double drawElapsed = cur - prev;
		prev = cur;

		acc += drawElapsed;

		
		while ( acc >= SPF ) {
			acc -= SPF;
			
			update(SPF);
		}

		//draw
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);

		MAT_SET_TRANSLATE(mat, xPos * scale, yPos * scale);

		glUniformMatrix3fv(U_TRANSFORM, 1, GL_FALSE, mat);

		p.draw();

		glfwSwapBuffers();
		
		
	}	

	glfwTerminate();
	return 0;
}

void getMousePos(float &mx, float &my) {
	int mouseX, mouseY;
	glfwGetMousePos(&mouseX, &mouseY);
	int w, h;
	glfwGetWindowSize(&w, &h);
	mx = (float)mouseX / w, my = (float)mouseY / h;
	mx = mx * 2 - 1;
	my = -(my * 2 - 1);
	mx /= scale;
	my /= scale;
}

void onMouseButton(int button, int action) {
	if ( button != GLFW_MOUSE_BUTTON_LEFT ) return;

	if ( action == GLFW_PRESS ) {
		float mx, my;
		getMousePos(mx, my);
		if ( abs(mx - xPos) < boxSize && abs(my - yPos) < boxSize ) {
			isDragging = true;
		}
	} else {
		isDragging = false;
		xVel = 0;
		xPrevPos = xPos;
	}
}

void onMouseMove(int x, int y) {
	if ( !isDragging ) return;

	float mx, my;
	getMousePos(mx, my);
	xPos = mx;
	xPrevPos = xPos;
}
