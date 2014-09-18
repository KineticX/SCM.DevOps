/*
 * Copyright (C) 2010 JFrog Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package artifactBuildinfo;

import hudson.Util;
import java.util.ArrayList;

import hudson.model.AbstractBuild;
import hudson.model.BuildBadgeAction;

/**
 * Result of the redeploy publisher. Currently only a link to Artifactory build info.
 *
 * @author Yossi Shaul
 */
public class BuildResultInfo implements BuildBadgeAction {

    private final String url;

    /**
     * @deprecated Only here to keep compatibility with version 1.0.7 and below (part of the xstream de-serialization)
     */
    @Deprecated
    private transient AbstractBuild build;
    public ArrayList<String> ArtifactList;
    public BuildResultInfo(ArrayList<String> Artifacts) {
        ArtifactList = Artifacts;
        url = getUrlName();
    }

    public String getIconFileName() {
        return "package.png";
    }

    public String getDisplayName() {
        return "Artifactory Download Links";
    }

    public String getUrlName() {

        String HtmlBlock = new String();
        for (String s : ArtifactList) {

            int endindex = s.length();
            String file = s.substring(s.lastIndexOf('/')+1, endindex);
            String fileURL = s.replace("Deploying artifact: ","");
            HtmlBlock = HtmlBlock + "<a href='"+fileURL+"'>"+file+"</a><br />";
        }
        return HtmlBlock;

    }

}
