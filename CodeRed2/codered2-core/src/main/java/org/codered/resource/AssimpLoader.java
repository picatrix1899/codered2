package org.codered.resource;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.system.MemoryUtil;
import org.barghos.math.api.vector.Vec2fC;
import org.barghos.math.api.vector.Vec3fC;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;
import org.codered.URLUtils;
import org.codered.engine.CriticalEngineStateError;

public class AssimpLoader
{
	
	public ModelData loadModel(URL url) throws Exception
	{
		try(InputStream stream = url.openStream())
		{
			return loadModel(stream);
		}
	}
	
	public ModelData loadModel(InputStream stream) throws Exception
	{
		byte[] bytes = stream.readAllBytes();

		ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		
		AIScene scene = Assimp.aiImportFileFromMemory(buffer, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs | Assimp.aiProcess_CalcTangentSpace, "obj");
		
		if(scene == null || (scene.mFlags() & Assimp.AI_SCENE_FLAGS_INCOMPLETE) > 0 || scene.mRootNode() == null)
		{
			throw new CriticalEngineStateError(Assimp.aiGetErrorString());
		}
		
		List<MeshData> meshes = new ArrayList<>();
		
		processNode(scene.mRootNode(), scene, meshes);
		
		ModelData model = new ModelData(meshes);
		
		MemoryUtil.memFree(buffer);
		
		return model;
	}
	
	public void processNode(AINode node, AIScene scene, List<MeshData> meshes) throws Exception
	{
		for(int i = 0; i < node.mNumMeshes(); i++)
		{
			int index = node.mMeshes().get(i);
			AIMesh mesh = AIMesh.create(scene.mMeshes().get(index));
			processMesh(scene, mesh, meshes);
		}

		for(int i = 0; i < node.mNumChildren(); i++)
		{
			AINode childNode = AINode.create(node.mChildren().get(i));
			processNode(childNode, scene, meshes);
		}
	}
	
	public void processMesh(AIScene scene, AIMesh rawMesh, List<MeshData> meshes) throws Exception
	{
		List<VertexData> vertices = new ArrayList<>();
		
		Vec3fC pos = new Vec3f();
		Vec3fC nrm = new Vec3f();
		Vec3fC tng = new Vec3f();
		Vec2fC uv = new Vec2f();
		
		for(int i = 0; i < rawMesh.mNumVertices(); i++)
		{
			AIVector3D vertex = rawMesh.mVertices().get(i);
			AIVector3D normal = rawMesh.mNormals().get(i);
			AIVector3D tangent = rawMesh.mTangents().get(i);
			
			pos.set(vertex.x(), vertex.y(), vertex.z());
			nrm.set(normal.x(), normal.y(), normal.z());
			tng.set(tangent.x(), tangent.y(), tangent.z());
			
			if(rawMesh.mTextureCoords(0) != null)
			{
				AIVector3D texCoords = rawMesh.mTextureCoords(0).get(i);
				uv.set(texCoords.x(), texCoords.y());
			}
			
			
			VertexData v = new VertexData(pos, nrm, tng, uv);
			vertices.add(v);
		}
		
		List<TriangleFaceData> faces = new ArrayList<>();
		for(int i = 0; i < rawMesh.mNumFaces(); i++)
		{
			AIFace face = rawMesh.mFaces().get(i);
			
			VertexData v1 = vertices.get(face.mIndices().get(0));
			VertexData v2 = vertices.get(face.mIndices().get(1));
			VertexData v3 = vertices.get(face.mIndices().get(2));
			
			Vec3fC tv1 = v2.getPosition().subN(v1.getPosition());
			Vec3fC tv2 = v3.getPosition().subN(v1.getPosition());
			
			Vec3fC n = tv1.crossN(tv2);
			
			Vec3fC vn1 = v1.getNormal();
			Vec3fC vn2 = v2.getNormal();
			Vec3fC vn3 = v3.getNormal();
			
			if((n.dot(vn1) < 0.0f && n.dot(vn2) < 0.0f) ||
				(n.dot(vn2) < 0.0f && n.dot(vn3) < 0.0f) ||
				(n.dot(vn1) < 0.0f && n.dot(vn3) < 0.0f))
			{
				n = tv2.crossN(tv1);
			}

			TriangleFaceData f = new TriangleFaceData(v1, v2, v3, n);
			faces.add(f);
		}

		AIMaterial mat = AIMaterial.create(scene.mMaterials().get(rawMesh.mMaterialIndex()));
		AIString pathDiffuse = AIString.calloc();
		Assimp.aiGetMaterialTexture(mat, Assimp.aiTextureType_DIFFUSE, 0, pathDiffuse, (IntBuffer) null, null, null, null, null, null);
		
		TextureData diffuse = null;
		TextureData normals = null;
		
		if(!pathDiffuse.dataString().isEmpty())
		{
			TextureData data = TextureLoader.loadResource(URLUtils.getEmbededResourceURL(pathDiffuse.dataString()));
			diffuse = data;
		}
		
		
		AIString pathNormals = AIString.calloc();
		Assimp.aiGetMaterialTexture(mat, Assimp.aiTextureType_NORMALS, 0, pathNormals, (IntBuffer) null, null, null, null, null, null);
		
		if(!pathNormals.dataString().isEmpty())
		{
			TextureData data = TextureLoader.loadResource(URLUtils.getEmbededResourceURL(pathNormals.dataString()));
			normals = data;
		}
		
		MaterialData material = new MaterialData(diffuse, normals);

		MeshData mesh = new MeshData(vertices.size(), faces, material);
		meshes.add(mesh);
	}
}
