package com.example.applemusicapicall;

import java.util.List;

public class Music {

    public int resultCount;
    public List<Result> results;

    public Music(List<Result> results) {
        this.results = results;
    }
}
