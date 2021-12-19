package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.List;

public class PullModelSource implements PullFilter<Face> {

    private List<Face> data;
    private int idx = -1;

    @Override
    public Face read() {
        // mittels index merkt sich das Programm den Index des nächsten zurückzugebenen Wertes.
        // Falls alle Werte zurückgeben worden sind, wird Null zurückgegeben.
        // Dadurch ist es möglich, pro Read ein einzelnes Faces zu lesen. Es muss nicht gleich die ganze Liste zurückgegeben werden.
        if (idx + 1 < data.size()) {
            idx = idx + 1;
            return data.get(idx);
        }
        return null;
    }

    // Wenn die Liste mit den Faces upgedatet wird, müssen wir auch den Index zurücksetzen, damit die neuen Faces zurückgegeben werden können.
    public void updateData(List<Face> data) {
        this.data = data;
        idx = -1;
    }
}
