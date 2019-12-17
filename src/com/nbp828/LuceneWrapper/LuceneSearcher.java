package com.nbp828.LuceneWrapper;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class LuceneSearcher {

    public ArrayList<LuceneResult> Search(String indexPath, String queryString)
            throws Exception
    {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new BM25Similarity());
        Analyzer analyzer = new StandardAnalyzer();
        String field = "contents";
        QueryParser parser = new QueryParser(field, analyzer);

        queryString = queryString.trim();
        if (queryString.length() == 0) {
            throw new IllegalArgumentException("bad query string");
        }

        Query query = parser.parse(queryString);
        System.out.println("Searching for: " + query.toString(field));

        ArrayList<LuceneResult> results = this.doPagingSearch(searcher, query, 10);
        return results;
    }

    private ArrayList<LuceneResult> doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage)
            throws IOException
    {
        ArrayList<LuceneResult> luceneResults = new ArrayList<>();
        TopDocs results = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;
        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            String path = doc.get("path");
            String[] dirs = path.split("/");
            String code = dirs[dirs.length - 1];
            LuceneResult result = new LuceneResult(code, hit.score);
            luceneResults.add(result);
        }
        return luceneResults;
    }


}