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

    /**
     * While not initialized now, the user will eventually need the following data:
     * <br>
     * String: username, pet name, first action, second action, and third action
     * <br>
     * int: colorRes - id for what image to display, depending on the color of the pet
     */
    public UserCardView() {
        // No args constructor, all variables are null
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

    public void setColorRes(int colorRes) {
        this.colorRes = colorRes;
    }

    public void setFirstAction(String firstAction) {
        this.firstAction = firstAction;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setSecondAction(String secondAction) {
        this.secondAction = secondAction;
    }

    public void setThirdAction(String thirdAction) {
        this.thirdAction = thirdAction;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
