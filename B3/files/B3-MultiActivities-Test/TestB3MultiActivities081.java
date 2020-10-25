package org.aplas.soccermatch;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB3MultiActivities081 extends ViewTest {
    private ActivityScenario<LogActivity> logScenario;
    private Intent logIntent;

    @Before
    public void initTest() {
        /****** initiation of Test ******/
        //Robolectric Pack
        MainActivity main = Robolectric.buildActivity(MainActivity.class).create().get();
        completingMainActivity(main);
        ((Button)getViewFromActivity("startBtn",main)).performClick();
        Intent playIntent = Shadows.shadowOf(main).getNextStartedActivity();
        PlayActivity play = Robolectric.buildActivity(PlayActivity.class,playIntent).create().get();
        completingPlayActivity(play);
        ((Button)getViewFromActivity("matchFinishBtn",play)).performClick();

        logIntent = Shadows.shadowOf(main).getNextStartedActivity();
        logScenario = ActivityScenario.launch(logIntent);
    }

    @Test
    public void check_01_Contents_onCreate() {
        logScenario.moveToState(Lifecycle.State.CREATED);
        logScenario.onActivity(activity -> {
            testFieldViewExist("logView","RecyclerView",activity);
            testFieldViewExist("restartBtn","AppCompatButton",activity);
        });
    }

    @Test
    public void check_02_Header_Result() {
        logScenario.moveToState(Lifecycle.State.STARTED);
        logScenario.onActivity(activity -> {
            String str = "Result: "+activity.getIntent().getStringExtra("MATCH_RESULT");
            assertEquals("TextView 'matchResultTxt' must show the result of match",
                    str, ((TextView)getViewFromActivity("matchResultTxt",activity)).getText().toString());

            str = "Score: "+activity.getIntent().getStringExtra("MATCH_SCORE");
            assertEquals("TextView 'matchScoreTxt' must show the score of match",
                    str, ((TextView)getViewFromActivity("matchScoreTxt",activity)).getText().toString());
        });
    }

    @Test
    public void check_03_LogView_RecyclerView() {
        logScenario.moveToState(Lifecycle.State.STARTED);
        logScenario.onActivity(activity -> {
            RecyclerView logView = (RecyclerView) getViewFromActivity("logRcView", activity);
            ArrayList<String> eventList = activity.getIntent().getStringArrayListExtra("MATCH_EVENT");

            assertEquals("RecyclerView 'logRcView' Layout Manager should be LinearLayoutManager",
                    "LinearLayoutManager", logView.getLayoutManager().getClass().getSimpleName());
            assertEquals("RecyclerView 'logRcView' Item Animator should be DefaultItemAnimator",
                    "DefaultItemAnimator", logView.getItemAnimator().getClass().getSimpleName());

            assertEquals("RecyclerView 'logRcView' adapter should be in LogAdapter type",
                    "LogAdapter", logView.getAdapter().getClass().getSimpleName());

            LogAdapter logAdapter = (LogAdapter) logView.getAdapter();
            assertTrue("RecyclerView 'logRcView' item count should be same with extra 'MATCH_EVENT'",
                    eventList.size() == logAdapter.getItemCount());

            for (int i = 0; i < logView.getChildCount(); i++) {
                //System.out.println(logAdapter.getItemCount()+"/ Loop ke-"+i);
                View view = logView.findViewHolderForAdapterPosition(i).itemView;
                String[] data = eventList.get(i).split("@");
                TextView txt = (TextView) getViewFromLayout("txtName", view);
                assertTrue("TextView 'txtName' in RecycleView must show event name",
                        data[0].equals(txt.getText().toString()));

                String icon = "";
                switch (data[0]) {
                    case "Goal":
                        icon = "icon_goal";
                        break;
                    case "Yellow Card":
                        icon = "icon_yellow_card";
                        break;
                    case "Red Card":
                        icon = "icon_red_card";
                        break;
                }
                assertTrue("ImageView 'eventIcon' in RecycleView must show related icon to event",
                        checkImageContent(getViewFromLayout("eventIcon", view), icon));

                txt = (TextView) getViewFromLayout("txtTime", view);
                assertTrue("TextView 'txtTime' in RecycleView must show event name",
                        data[3].equals(txt.getText().toString()));
                txt = (TextView) getViewFromLayout("txtPlayer", view);
                assertTrue("TextView 'txtPlayer' in RecycleView must show event name",
                        (data[1] + " (" + data[2] + ")").equals(txt.getText().toString()));
            }

        });
    }

    @Test
    public void check_04_RestartBtn_Click () {
        logScenario.moveToState(Lifecycle.State.STARTED);
        logScenario.onActivity(activity -> {
            ((Button)getViewFromActivity("restartBtn",activity)).performClick();
            Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();
            Assert.assertNotNull("Intent 'MainActivity' should be activated",actual);
            Assert.assertEquals("Intent 'MainActivity' should be activated",".MainActivity",actual.getComponent().getShortClassName());
        });
    }


}
