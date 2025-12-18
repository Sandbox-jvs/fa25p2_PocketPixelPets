/** Model Class for the admin recycler view on the Admin page
 * @author Cristian Perez
 * @since 12/17/25
 */

package com.talentengine.pocketpixelpets;

import androidx.annotation.DrawableRes;

public class UserCardView {
    private String username;
    private String petName;
    private String firstAction;
    private String secondAction;
    private String thirdAction;

    // The drawable sprite for the pet color
   @DrawableRes
   private int colorRes;

    public UserCardView(int colorRes, String firstAction, String petName, String secondAction, String thirdAction, String username) {
        this.colorRes = colorRes;
        this.firstAction = firstAction;
        this.petName = petName;
        this.secondAction = secondAction;
        this.thirdAction = thirdAction;
        this.username = username;
    }

    public int getColorRes() {
        return colorRes;
    }

    public String getFirstAction() {
        return firstAction;
    }

    public String getPetName() {
        return petName;
    }

    public String getSecondAction() {
        return secondAction;
    }

    public String getThirdAction() {
        return thirdAction;
    }

    public String getUsername() {
        return username;
    }
}
