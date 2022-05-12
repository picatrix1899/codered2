package org.codered.game;

import static org.barghos.core.api.tuple.TupleConstants.*;

import org.barghos.core.api.tuple3.Tup3fC;
import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.core.api.util.ExtractParam;
import org.barghos.math.api.matrix.Mat4fC;
import org.barghos.math.api.vector.Vec3fC;

public interface Camera3f
{
	Camera3f update();
	
	default Vec3fC getPos()
	{
		return getPos(1.0f);
	}
	
	default <T extends Tup3fC> T getPos(@ExtractParam T res)
	{
		return getPos(1.0f, res);
	}
	
	default float[] getPos(@ExtractParam float[] res)
	{
		return getPos(1.0f, res);
	}
	
	Vec3fC getPos(float alpha);
	
	<T extends Tup3fC> T getPos(float alpha, @ExtractParam T res);
	
	float[] getPos(float alpha, @ExtractParam float[] res);
	
	default Vec3fC getRight()
	{
		return getRight(1.0f);
	}
	
	default <T extends Tup3fC> T getRight(@ExtractParam T res)
	{
		return getRight(1.0f, res);
	}
	
	default float[] getRight(@ExtractParam float[] res)
	{
		return getRight(1.0f, res);
	}
	
	Vec3fC getRight(float alpha);
	
	<T extends Tup3fC> T getRight(float alpha, @ExtractParam T res);
	
	float[] getRight(float alpha, @ExtractParam float[] res);
	
	default Vec3fC getLeft()
	{
		return getLeft(1.0f);
	}
	
	default <T extends Tup3fC> T getLeft(@ExtractParam T res)
	{
		return getLeft(1.0f, res);
	}
	
	default float[] getLeft(@ExtractParam float[] res)
	{
		return getLeft(1.0f, res);
	}
	
	Vec3fC getLeft(float alpha);
	
	<T extends Tup3fC> T getLeft(float alpha, @ExtractParam T res);
	
	float[] getLeft(float alpha, @ExtractParam float[] res);
	
	default Vec3fC getUp()
	{
		return getUp(1.0f);
	}
	
	default <T extends Tup3fC> T getUp(@ExtractParam T res)
	{
		return getUp(1.0f, res);
	}
	
	default float[] getUp(@ExtractParam float[] res)
	{
		return getUp(1.0f, res);
	}
	
	Vec3fC getUp(float alpha);
	
	<T extends Tup3fC> T getUp(float alpha, @ExtractParam T res);
	
	float[] getUp(float alpha, @ExtractParam float[] res);
	
	default Vec3fC getDown()
	{
		return getDown(1.0f);
	}
	
	default <T extends Tup3fC> T getDown(@ExtractParam T res)
	{
		return getDown(1.0f, res);
	}
	
	default float[] getDown(@ExtractParam float[] res)
	{
		return getDown(1.0f, res);
	}
	
	Vec3fC getDown(float alpha);
	
	<T extends Tup3fC> T getDown(float alpha, @ExtractParam T res);
	
	float[] getDown(float alpha, @ExtractParam float[] res);
	
	default Vec3fC getForward()
	{
		return getForward(1.0f);
	}
	
	default <T extends Tup3fC> T getForward(@ExtractParam T res)
	{
		return getForward(1.0f, res);
	}
	
	default float[] getForward(@ExtractParam float[] res)
	{
		return getForward(1.0f, res);
	}
	
	Vec3fC getForward(float alpha);
	
	<T extends Tup3fC> T getForward(float alpha, @ExtractParam T res);
	
	float[] getForward(float alpha, @ExtractParam float[] res);
	
	default Vec3fC getBack()
	{
		return getBack(1.0f);
	}
	
	default <T extends Tup3fC> T getBack(@ExtractParam T res)
	{
		return getBack(1.0f, res);
	}
	
	default float[] getBack(@ExtractParam float[] res)
	{
		return getBack(1.0f, res);
	}
	
	Vec3fC getBack(float alpha);
	
	<T extends Tup3fC> T getBack(float alpha, @ExtractParam T res);
	
	float[] getBack(float alpha, @ExtractParam float[] res);
	
	Mat4fC getViewMatrix(float alpha);
	
	<T extends Mat4fC> T getViewMatrix(float alpha, @ExtractParam T res);
	
	default Camera3f setPos(Tup3fR p)
	{
		return setPos(p.getX(), p.getY(), p.getZ());
	}
	
	default Camera3f setPos(float[] p)
	{
		return setPos(p[COMP_X], p[COMP_Y], p[COMP_Z]);
	}
	
	Camera3f setPos(float x, float y, float z);
	
	default Camera3f setRight(Tup3fR v)
	{
		return setRight(v.getX(), v.getY(), v.getZ());
	}
	
	default Camera3f setRight(float[] v)
	{
		return setRight(v[COMP_X], v[COMP_Y], v[COMP_Z]);
	}
	
	Camera3f setRight(float x, float y, float z);
	
	default Camera3f setUp(Tup3fR v)
	{
		return setUp(v.getX(), v.getY(), v.getZ());
	}
	
	default Camera3f setUp(float[] v)
	{
		return setUp(v[COMP_X], v[COMP_Y], v[COMP_Z]);
	}
	
	Camera3f setUp(float x, float y, float z);
	
	default Camera3f setForward(Tup3fR v)
	{
		return setForward(v.getX(), v.getY(), v.getZ());
	}
	
	default Camera3f setForward(float[] v)
	{
		return setForward(v[COMP_X], v[COMP_Y], v[COMP_Z]);
	}
	
	Camera3f setForward(float x, float y, float z);
	
	default Camera3f move(Tup3fR v)
	{
		return move(v.getX(), v.getY(), v.getZ());
	}
	
	default Camera3f move(float[] v)
	{
		return move(v[COMP_X], v[COMP_Y], v[COMP_Z]);
	}
	
	Camera3f move(float x, float y, float z);
	
	default Camera3f rotate(Tup3fR axis, float angle)
	{
		return rotate(axis.getX(), axis.getY(), axis.getZ(), angle);
	}
	
	default Camera3f rotate(float[] axis, float angle)
	{
		return rotate(axis[COMP_X], axis[COMP_Y], axis[COMP_Z], angle);
	}
	
	Camera3f rotate(float x, float y, float z, float angle);
}
