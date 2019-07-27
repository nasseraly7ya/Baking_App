package com.example.bakingapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements ExoPlayer.EventListener {
    PlayerView playerView;
    TextView stepsText ;
    ExoPlayer exoPlayer;
    Steps step;
    Uri uri;
    private int window=0;
    private boolean state;

    public boolean getState() {
       return state;
    }

    public long getVideoPos() {
        if (exoPlayer!=null){
        return exoPlayer.getContentPosition();}
        else return 0;
    }


    public void setVideoPos(long videoPos) {
        this.videoPos = videoPos;
    }

    private long videoPos=0;
    StepsActivity stepsActivity = null;

    public StepsActivity getStepsActivity() {
        return stepsActivity;
    }

    public void setStepsActivity(StepsActivity stepsActivity) {
        this.stepsActivity = stepsActivity;
    }
// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            Log.v("onCreate","yes");
            window=savedInstanceState.getInt("window");

        }
        View view =inflater.inflate(R.layout.fragment_blank, container, false);
        // Inflate the layout for this fragment
        stepsText = view.findViewById(R.id.stepText);
        Bundle bundle = getArguments();
            videoPos=bundle.getLong("videoPos");
            state=bundle.getBoolean("state");
            step = (bundle.getParcelable("steps"));
           String url = step.getVideoURL();
        playerView = view.findViewById(R.id.vidoe);
        Log.v("URI", step.getVideoURL());

            if(!url.isEmpty() ) {
                uri = Uri.parse(step.getVideoURL());
                playerView.setVisibility(View.VISIBLE);
                beginVidoe(videoPos,state);
            }else {
                playerView.setVisibility(View.GONE);

            }

            Log.v("step ", step.toString());
            if (stepsText != null)
            stepsText.setText(step.getDescroption());

            return view;

    }
    public void beginVidoe(Long pos,boolean state){

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer= ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
        exoPlayer.addListener(this);

        playerView.setPlayer(exoPlayer);


        String userAgent = Util.getUserAgent(getContext(),getString(R.string.app_name));
        MediaSource mediaSource= new ExtractorMediaSource(uri,new DefaultDataSourceFactory(getContext(),userAgent),new DefaultExtractorsFactory(),null,null);

        exoPlayer.prepare(mediaSource);

        exoPlayer.setPlayWhenReady(state);

        exoPlayer.seekTo(pos);
        Log.v("videostate",String.valueOf(exoPlayer.getPlaybackState()));






    }


    @Override
    public void onDetach() {
        if (exoPlayer!= null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer=null;

        }
        super.onDetach();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }


    @Override
    public void onStop() {
        if (exoPlayer!= null){
            onDetach();}
        super.onStop();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        state=playWhenReady;
        Log.v("stteVideo",String.valueOf(playbackState));
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
