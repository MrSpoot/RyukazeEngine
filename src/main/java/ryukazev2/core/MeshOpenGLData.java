package ryukazev2.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeshOpenGLData {

    private int vao;
    private int vbo;
    private int meshCount;

}
