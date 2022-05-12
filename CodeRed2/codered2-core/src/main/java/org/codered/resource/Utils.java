package org.codered.resource;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.barghos.core.api.util.BufferUtil;
import org.codered.Model;
import org.codered.rendering.VAO;
import org.codered.rendering.VAOPointers;
import org.codered.rendering.VBO;

public class Utils
{
	public static Model createModel(ModelData data)
	{
		ModelData mdata = data;
		
		List<Mesh> meshes = new ArrayList<>();

		for(MeshData dmesh : mdata.getMeshes())
		{
			FloatBuffer bp = BufferUtil.createFloatBuffer(dmesh.getVertexCount() * 3);
			FloatBuffer bn = BufferUtil.createFloatBuffer(dmesh.getVertexCount() * 3);
			FloatBuffer buv = BufferUtil.createFloatBuffer(dmesh.getVertexCount() * 2);
			FloatBuffer bt = BufferUtil.createFloatBuffer(dmesh.getVertexCount() * 3);
			IntBuffer bind = BufferUtil.createIntBuffer(dmesh.getVertexCount() * 3);
			
			int vIndex = 0;
			for(TriangleFaceData dFace : dmesh.getFaces())
			{
				BufferUtil.putTuple3f(bp, dFace.getVertexA().getPosition(), dFace.getVertexB().getPosition(), dFace.getVertexC().getPosition());
				BufferUtil.putTuple3f(bn, dFace.getVertexA().getNormal(), dFace.getVertexB().getNormal(), dFace.getVertexC().getNormal());
				BufferUtil.putTuple3f(bt, dFace.getVertexA().getTangent(), dFace.getVertexB().getTangent(), dFace.getVertexC().getTangent());

				buv.put(dFace.getVertexA().getUV().x);
				buv.put(dFace.getVertexA().getUV().y);
				buv.put(dFace.getVertexB().getUV().x);
				buv.put(dFace.getVertexB().getUV().y);
				buv.put(dFace.getVertexC().getUV().x);
				buv.put(dFace.getVertexC().getUV().y);

				bind.put(vIndex++);
				bind.put(vIndex++);
				bind.put(vIndex++);
			}

			bp.flip();
			buv.flip();
			bn.flip();
			bt.flip();
			bind.flip();
			
			VBO vboPos = new VBO();
			vboPos.bind(GL15.GL_ARRAY_BUFFER);
			vboPos.storeData(bp, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
			vboPos.unbind(GL15.GL_ARRAY_BUFFER);
			
			VAOPointers vaopPos = new VAOPointers();
			vaopPos.add(0, 3, GL11.GL_FLOAT, false, 0, 0);
					
			VBO vboUV = new VBO();
			vboUV.bind(GL15.GL_ARRAY_BUFFER);
			vboUV.storeData(buv, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
			vboUV.unbind(GL15.GL_ARRAY_BUFFER);
			
			VAOPointers vaopUV = new VAOPointers();
			vaopUV.add(1, 2, GL11.GL_FLOAT, false, 0, 0);
			
			VBO vboNormal = new VBO();
			vboNormal.bind(GL15.GL_ARRAY_BUFFER);
			vboNormal.storeData(bn, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
			vboNormal.unbind(GL15.GL_ARRAY_BUFFER);
			
			VAOPointers vaopNormal = new VAOPointers();
			vaopNormal.add(2, 3, GL11.GL_FLOAT, false, 0, 0);
			
			VBO vboTangent = new VBO();
			vboTangent.bind(GL15.GL_ARRAY_BUFFER);
			vboTangent.storeData(bt, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
			vboTangent.unbind(GL15.GL_ARRAY_BUFFER);
			
			VAOPointers vaopTangent = new VAOPointers();
			vaopTangent.add(3, 3, GL11.GL_FLOAT, false, 0, 0);
			
			VAO vao = new VAO();
			vao.bind();
			
			vao.setPointersForBuffer(vboPos, GL15.GL_ARRAY_BUFFER, vaopPos);
			vao.setPointersForBuffer(vboUV, GL15.GL_ARRAY_BUFFER, vaopUV);
			vao.setPointersForBuffer(vboNormal, GL15.GL_ARRAY_BUFFER, vaopNormal);
			vao.setPointersForBuffer(vboTangent, GL15.GL_ARRAY_BUFFER, vaopTangent);
			
			vao.storeIndices(bind, GL15.GL_STATIC_DRAW);

			vao.unbind();
			
			BufferUtil.destroyBuffer(bp);
			BufferUtil.destroyBuffer(buv);
			BufferUtil.destroyBuffer(bn);
			BufferUtil.destroyBuffer(bt);
			
			meshes.add(new Mesh(vao, dmesh.getVertexCount()));
		}
		
		return new Model(meshes);
	}
}
