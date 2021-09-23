package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Logging.Logger;
import com.experis.de.ChinookTunes.Models.Track;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrackRepositoryImpl implements TrackRepository {

    private final Logger logger;
    private SqlLiteHelper db;

    public TrackRepositoryImpl(Logger logger) {
        this.logger = logger;
        this.db = new SqlLiteHelper(logger);
    }

    @Override
    public List<Track> getAllTracks(Integer limit, Integer offset, String orderBy) {
        ArrayList<Track> tracks = null;

        try {
            String sql = db.prepareSql("select TrackId, Name as TrackName from Track",
                    limit, offset, orderBy);

            ResultSet resultSet = db.executeQuery(sql);

            // Process Results
            tracks = new ArrayList<>();
            while (resultSet.next()) {
                tracks.add(resultSetToTrack(resultSet));
            }
        }
        catch (Exception ex){
             logger.log("getAllTracks() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return tracks;
    }

    @Override
    public List<Track> searchTracks(String searchText) {
        ArrayList<Track> tracks = null;

        try {
            String sql = "select TrackId, Track.Name as TrackName, Album.Title as AlbumTitle, Artist.Name as ArtistName, Genre.Name as GenreName from Track" +
                    " inner join Album on Album.AlbumId = Track.AlbumId" +
                    " inner join Artist on Artist.ArtistId = Album.ArtistId" +
                    " inner join Genre on Genre.GenreId = Track.GenreId" +
                    " where Track.Name like ?";

            ResultSet resultSet = db.executeQuery(sql, '%'+searchText+'%');

            // Process Results
            tracks = new ArrayList<>();
            while (resultSet.next()) {
                tracks.add(resultSetToTrack(resultSet));
            }
        }
        catch (Exception ex){
             logger.log("searchTracks() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return tracks;
    }

    @Override
    public Track getTrackById(Integer trackId) {
        Track track = null;

        try {
            ResultSet resultSet = db.executeQuery("select TrackId, Name as TrackName WHERE TrackId = ?", trackId);

            // Process Results
            track = resultSetToTrack(resultSet);
        }
        catch (Exception ex){
             logger.log("getTrackById("+trackId+") failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return track;
    }

    private Track resultSetToTrack(ResultSet resultSet) throws SQLException {
        Track track = null;

        if (resultSet != null) {
            track = new Track(
                    resultSet.getInt("TrackId"),
                    resultSet.getString("TrackName"),
                    db.tryGetString(resultSet, "AlbumTitle"),
                    db.tryGetString(resultSet, "ArtistName"),
                    db.tryGetString(resultSet, "GenreName")
            );
        }
        return track;
    }
}
