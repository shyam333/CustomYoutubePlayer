package com.abiramiaudio.standaloneplayer;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.OnFullscreenListener,View.OnClickListener {

    YouTubePlayer playa;
   // Button play, pause;
    private TextView mPlayTimeTextView;
    private View mPlayButtonLayout;
    private Handler mHandler = null;
    private SeekBar mSeekBar;
    public static final String API_KEY = "AIzaSyC58N3RJE6bqvMPKpc6zURD7pCiqLgPmm4";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static final String VIDEO_ID = "-m3V8w_7vhk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_player);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(API_KEY, this);

       // play = (Button) findViewById(R.id.play_button);
       // pause = (Button) findViewById(R.id.pause_button);

        mPlayButtonLayout = findViewById(R.id.video_control);
        findViewById(R.id.play_video).setOnClickListener(this);
        findViewById(R.id.pause_video).setOnClickListener(this);
       // play.setOnClickListener(this);
       // pause.setOnClickListener(this);

//        YouTubePlayerView youTubePlayerView =
//                (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);

        mHandler = new Handler();

//        youTubePlayerView.initialize("AIzaSyC58N3RJE6bqvMPKpc6zURD7pCiqLgPmm4",
//                new YouTubePlayer.OnInitializedListener() {
//                    @Override
//                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
//                                                        YouTubePlayer youTubePlayer, boolean wasRestored) {
//
//                        // do any work here to cue video, play video, etc.
//                        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
//                        youTubePlayer.setOnFullscreenListener(this);
//
//                        if (null == youTubePlayer) return;
//                        playa = youTubePlayer;
//
//                       // playa.getCurrentTimeMillis();
//                        displayCurrentTime();
//
//                        // Start buffering
//                        if (!wasRestored) {
//                            playa.cueVideo("5xVh-7ywKpE");
//                        }
//
////                        displayCurrentTime();
////
////                        playa = youTubePlayer;
////
////                       // playa.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
////
////                        youTubePlayer.cueVideo("5xVh-7ywKpE");
//
//                        playa.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//                        mPlayButtonLayout.setVisibility(View.VISIBLE);
//
//                        playa.setPlayerStateChangeListener(mPlayerStateChangeListener);
//                        playa.setPlaybackEventListener(mPlaybackEventListener);
//
//
//                    }
//
//                    @Override
//                    public void onInitializationFailure(YouTubePlayer.Provider provider,
//                                                        YouTubeInitializationResult youTubeInitializationResult) {
//
//                    }
//                });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);

        if (null == player) return;
        playa = player;

       // displayCurrentTime();

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        mPlayButtonLayout.setVisibility(View.VISIBLE);

        // Add listeners to YouTubePlayer instance
        player.setPlayerStateChangeListener(mPlayerStateChangeListener);
        player.setPlaybackEventListener(mPlaybackEventListener);
    }

//    @Override
//    public void onClick(View view) {
//
//        if (view == play_video) {
//            playa.play();
//        } else if (view == pause_video) {
//            playa.pause();
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_video:
                if (null != playa && !playa.isPlaying())
                    playa.play();
                break;
            case R.id.pause_video:
                if (null != playa && playa.isPlaying())
                    playa.pause();
                break;
        }
    }

    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
//            mHandler.postDelayed(runnable, 100);
//            displayCurrentTime();
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {
            mHandler.postDelayed(runnable, 100);
        }
    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
           // displayCurrentTime();
        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//            long lengthPlayed = (playa.getDurationMillis() * progress) / 100;
//            playa.seekToMillis((int) lengthPlayed);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

//    private void displayCurrentTime() {
//        if (null == playa) return;
//        String formattedTime = formatTime(playa.getDurationMillis() - playa.getCurrentTimeMillis());
//        mPlayTimeTextView.setText(formattedTime);
//    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "--:" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
           // displayCurrentTime();
            mHandler.postDelayed(this,100);
        }
    };

    @Override
    public void onFullscreen(boolean b) {


    }
}
