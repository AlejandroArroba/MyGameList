package mygamelist.Response;

import mygamelist.Model.JuegoModel;

import java.util.List;

public class RawgResponse {

    private List<JuegoModel> results;

    public List<JuegoModel> getResults() {
        return results;
    }

    public void setResults(List<JuegoModel> results) {
        this.results = results;
    }

}
