package org.spoot.ryukazev2.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.spoot.ryukazev2.graphics.Material;

@Data
@AllArgsConstructor
public class MeshOpenGLData {

    private int vao;
    private int vbo;
    private Material material;

}
