package mygamelist.Response;

import mygamelist.Model.Juego;

import java.util.List;

public class RawgResponse {

    private List<Juego> results;

    public List<Juego> getResults() {
        return results;
    }

    public void setResults(List<Juego> results) {
        this.results = results;
    }

}
