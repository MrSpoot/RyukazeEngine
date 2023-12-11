package ryukazev2.core.utils;

import lombok.Getter;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SceneData {

    @Getter
    private final int vao;
    private final List<Integer> vbos;


    public SceneData(){
        vao = glGenVertexArrays();
        vbos = new ArrayList<>();
    }

    public void addVBO(Integer vbo){
        this.vbos.add(vbo);
    }

}
