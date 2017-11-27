package com.kuruvatech.kumarannajds.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.RecyclerItemClickListener;
import com.kuruvatech.kumarannajds.SingleViewActivity;
import com.kuruvatech.kumarannajds.adapter.Adapter;
import com.kuruvatech.kumarannajds.model.FeedItem;
import com.kuruvatech.kumarannajds.utils.ImageLoader;
import com.kuruvatech.kumarannajds.utils.SessionManager;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class JdsManifestoFragment extends Fragment{

    View rootview;
    SessionManager session;
    FeedItem feedItem;
    TextView description;
    TextView feedheading;
    ImageView imageshareButton;
    public ImageLoader imageLoader;
    // Button btnBack;
    RecyclerView recyclerView;
    Adapter adapter;
    FrameLayout frameLayout;;
    ImageView imagePlayBotton;
    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;

    PDFView pdfView;

    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
    public static final String SAMPLE_FILE = "KARNATAKA-KANNADA.pdf";
    String pdfName = "sample.pdf";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.manifesto, container, false);
        session = new SessionManager(getActivity().getApplicationContext());
        //Button button = (Button) rootview.findViewById(R.id.bPressMe);

        imageLoader = new ImageLoader(getContext(),500,500);
       // Intent intent = getIntent();
//        initContent();
//        Gson gson = new Gson();
//        //feedItem = gson.fromJson(intent.getStringExtra("FeedItem"), FeedItem.class);
//        description= (TextView) rootview.findViewById(R.id.detail_feed_description);
//        imageshareButton= (ImageView) rootview.findViewById(R.id.detail_shareit );
//        feedheading= (TextView) rootview.findViewById(R.id.detail_feed_name);
//        recyclerView = (RecyclerView) rootview.findViewById(R.id.detail_recycler_view);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        // provide our CustomSpanSizeLookup which determines how many spans each item in grid will occupy
//        gridLayoutManager.setSpanSizeLookup(new CustomSpanSizeLookup());
//        recyclerView.setLayoutManager(gridLayoutManager);
//        adapter = new Adapter(getActivity(),feedItem.getFeedimages());
//        recyclerView.setAdapter(adapter);
//        frameLayout = (FrameLayout)rootview.findViewById(R.id.youtube_frame);
//        imagePlayBotton = (ImageView)rootview.findViewById(R.id.play_video);

//        if( feedItem.getVideoid().length() > 0) {
//            youTubeThumbnailView = (YouTubeThumbnailView)rootview.findViewById(R.id.youtubethumbnailview);
//            youTubeThumbnailView.setTag(feedItem.getVideoid());
//            youTubeThumbnailView.initialize(API_KEY, this);
//            youTubeThumbnailView.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View arg0) {
//                    Intent i = new Intent(getContext(), YouTubePlayerFragmentActivity.class);
//                    i.putExtra("VIDEO_ID", feedItem.getVideoid());
//                    startActivity(i);
//                }});
//        }
//        else {
//            frameLayout.setVisibility(View.GONE);
//            imagePlayBotton.setVisibility(View.GONE);
//        }

//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext(),0,recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position2, String myposition) {
//                        Intent i = new Intent(getContext(), SingleViewActivity.class);
//                        i.putExtra("url", feedItem.getFeedimages().get(position2));
//                        startActivity(i);
//                    }
//
//                    @Override public void onLongItemClick(View view, int position2) {
//
//
//                    }
//                })
//        );

        //  btnBack = (Button) findViewById(R.id.back_button);
//        description.setText(feedItem.getDescription());
//        feedheading.setText(feedItem.getHeading());
        //imageLoader.DisplayImage(feedItem.getFeedimages().get(0), imageView);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                onBackPressed();
//            }
//        });
//        pdfView.fromAsset(SAMPLE_FILE)
//                .pages(0, 2, 1, 3, 3, 3)
//                .defaultPage(1)
//                .showMinimap(false)
//                .enableSwipe(true)
////                .onDraw(onDrawListener)
////                .onLoad(onLoadCompleteListener)
////                .onPageChange(onPageChangeListener)
//                .load();
        //SAMPLE_FILE
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
try{
                  // File pdfFile = createFileFromInputStream(getContext().getAssets().open(SAMPLE_FILE));
                    File pdfFile = new File("//android_asset//KARNATAKA-KANNADA.pdf");
                    if (pdfFile.exists()) {

                        Uri path = Uri.fromFile(pdfFile);

                        Intent objIntent = new Intent(Intent.ACTION_VIEW);

                        objIntent.setDataAndType(path, "application/pdf");

                        objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(objIntent);

                    } else {

                        Toast.makeText(getActivity(), "File NotFound",

                                Toast.LENGTH_SHORT).show();

                    }

                } catch (ActivityNotFoundException e) {

                    Toast.makeText(getActivity(),

                            "No Viewer Application Found", Toast.LENGTH_SHORT)

                            .show();

                } catch (Exception e) {

                    e.printStackTrace();

                }
//            }
//            });




//        imageshareButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                Intent shareIntent = new Intent();
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.getHeading());
//                shareIntent.putExtra(Intent.EXTRA_TEXT, feedItem.getDescription());
//
//                shareIntent.setAction(Intent.ACTION_SEND);
//
//                if(feedItem.getFeedimages().size() > 0)
//                {
//                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
//                    for(int i = 0 ; i< adapter.getFilePaths().size() && i < 1 ;i++)
//                    {
//                        //Uri imageFilePath = Uri.parse(adapter.getFilePaths().get(i));
//                        imageUris.add(Uri.parse(adapter.getFilePaths().get(i)));
//                        // Toast.makeText(FeedDetail.this, adapter.getFilePaths().get(i), Toast.LENGTH_SHORT).show();
//                    }
//                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
//                    shareIntent.setType("image/*");
//                }
//                else
//                {
//                    shareIntent.setType("text/plain");
//                }
//                startActivity(Intent.createChooser(shareIntent, "Share it ...."));
//                //startActivity(Intent.createChooser(sendIntent, "Share link!"));
//            }
//        });

        return rootview;
    }
    private File createFileFromInputStream(InputStream inputStream) {

        try{
            File f = new File(pdfName);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
        }

        return null;
    }
    private void initContent() {
        feedItem =  new FeedItem();
        feedItem.setDescription("Haradanahalli Devegowda Kumaraswamy is a young and impeccable polititician of Karnataka.He is one of the sons of former Prime Minister Sri. H.D. Devegowda .He is popularly known as Kumaranna among his friends and followers he is one of the great politician who have huge Followers because of his dynamic approach.\n" +
                "\n" +
                "Kumaraswamy is the 18th Chief minister of Karnataka .When initially Kumaraswamy took over as the Chief Minister everyone thought he is a chip of old block doing nothing but paying lip sympathy to the rural poor and attacking anything urban. But he proved everyone wrong, his priorities were clear from day 1 development of the state was top of his agenda no rural-urban divide. he put Bangalore back on the rails. he made it clear that the industries are given better facility so that Bangalore should be investor friendly. He also cleared all infrastructure projects\n" +
                "\n" +
                "He started touring the nooks and corners of the state interacted with the people took note of their problem and instructed officials to help them with a warning that he would visit again and seek action taken report .In Bangalore he started weekly Janatha Darshana which was biggest popular programe\n" +
                "\n" +
                "Kumaraswamy has effectively established his credentials through people friendly programes benefiting both rural and urban masses, he also unwearyingly supported the industries and was also committed towards improving infrastructure and facilities in Karnataka he gave top priority to up gradation of the infrastructure in Bangalore as well as those areas in the outskirts of Bangalore by taking up several projects he also extended his cooperation to IT & BT industries which were responsible for Bangalore to carve a name on the world map. Kumaraswamy was popular among people because of pro-people programes like village stay and onetime loan waiver to farmers ,arrack and lottery ban bicycle for girl students , Janatha darshana.");
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private static class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int i) {

//            if(i == 0 || i == 1) {
//                // grid items on positions 0 and 1 will occupy 2 spans of the grid
//                return 2;
//            } else {
//                // the rest of the items will behave normally and occupy only 1 span
            return 2;
//            }
        }
    }

}