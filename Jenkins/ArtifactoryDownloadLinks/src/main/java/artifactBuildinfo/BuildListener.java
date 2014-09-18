package artifactBuildinfo;

import hudson.AbortException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import java.util.List;
import hudson.Extension;
import hudson.model.Result;
import hudson.model.TaskListener;
import hudson.model.Run;
import java.util.logging.Level;
import java.util.logging.Logger;


import hudson.model.listeners.RunListener;

import static hudson.model.Result.SUCCESS;


@Extension
@SuppressWarnings("rawtypes")
public class BuildListener extends RunListener<Run> {

    public BuildListener() {
        super(Run.class);
    }

    @Override
    public void onCompleted(Run r, TaskListener listener) {

        // SUCCESS || FAILURE (String)
        String sStatus = getStatus(r);

        LOGGER.log(Level.WARNING, "Deploy Job Name:"+sStatus);

        if (sStatus == "SUCCESS") {

            ArrayList<String> Artifacts = getArtifactoryPublishedModulesURL(r);
            r.getActions().add(new BuildResultInfo(Artifacts));


        } else {



        }

    }

    private ArrayList<String> getArtifactoryPublishedModulesURL(Run r) {

      ArrayList<String> ArtifactURLs =  getMatches(r.getLogFile(), "Deploying artifact:");

        //LOGGER.log(Level.WARNING, "Artifact List: "+ArtifactURLs);

        for (String s : ArtifactURLs) {

            LOGGER.log(Level.WARNING, "Artifact:"+s);


        }
        return ArtifactURLs;
    }

    public ArrayList<String> getMatches(File f, String regexp) {

        //LOGGER.log(Level.WARNING, "Searching for:"+regexp+" in "+f);
        ArrayList<String> aArtifactList = new ArrayList<String>();
        BufferedReader reader = null;
        try {


            // Assume default encoding and text files
            String line;
            reader = new BufferedReader(new FileReader(f));
            while ((line = reader.readLine()) != null) {


                //LOGGER.log(Level.WARNING, "Line:"+line);

                if (line.contains(regexp)) {

                    String regexPattern = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
                    Pattern p = Pattern.compile(regexPattern);
                    Matcher m = p.matcher(line);
                    while(m.find()) {
                        String urlStr = m.group();

                        //LOGGER.log(Level.WARNING, "REGEX MATCHED:"+urlStr);

                        aArtifactList.add(urlStr);
                    }


                }

            }
        } catch (IOException e) {
                 } finally {

        }
        return aArtifactList;
    }


    private String getStatus(Run r) {
        Result result = r.getResult();
        String status = null;
        if (result != null) {
            status = result.toString();
        }
        return status;
    }

    private static final Logger LOGGER = Logger.getLogger(BuildListener.class.getName());

}