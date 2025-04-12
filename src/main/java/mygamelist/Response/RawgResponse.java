package mygamelist.Response;

import mygamelist.Model.Juego;

import java.util.List;

public class RawgResponse {

    private List<Juego> results;

    public List<Juego> getResult() {
        return results;
    }

    public void setResult(List<Juego> result) {
        this.results = result;
    }

}
