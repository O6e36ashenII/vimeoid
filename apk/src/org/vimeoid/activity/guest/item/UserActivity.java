/**
 * 
 */
package org.vimeoid.activity.guest.item;

import org.vimeoid.R;
import org.vimeoid.activity.guest.ItemActivity;
import org.vimeoid.adapter.ActionItem;
import org.vimeoid.adapter.SectionedActionsAdapter;
import org.vimeoid.dto.simple.UserInfo;
import org.vimeoid.util.Dialogs;
import org.vimeoid.util.Utils;

import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <dl>
 * <dt>Project:</dt> <dd>vimeoid</dd>
 * <dt>Package:</dt> <dd>org.vimeoid.activity.guest.item</dd>
 * </dl>
 *
 * <code>UserActivity</code>
 *
 * <p>Description</p>
 *
 * @author Ulric Wilfred <shaman.sir@gmail.com>
 * @date Sep 16, 2010 6:38:02 PM 
 *
 */
public class UserActivity extends ItemActivity<UserInfo> {

    public UserActivity() {
        super(R.layout.view_single_user, UserInfo.FULL_EXTRACT_PROJECTION);
    }

    @Override
    protected UserInfo extractFromCursor(Cursor cursor, int position) {
        return UserInfo.fullFromCursor(cursor, position);
    }
    
    protected void initTitleBar(ImageView subjectIcon, TextView subjectTitle, ImageView resultIcon) {
        super.initTitleBar(subjectIcon, subjectTitle, resultIcon);
        if (getIntent().hasExtra(Utils.USERNAME_EXTRA)) {
            subjectTitle.setText(getIntent().getStringExtra(Utils.USERNAME_EXTRA));
        }
    }
    
    @Override
    protected void onItemReceived(final UserInfo user) {
        
        Log.d(TAG, "user " + user.id + " data received, name: " + user.displayName);
        ((TextView)titleBar.findViewById(R.id.subjectTitle)).setText(user.displayName);
        
        // biography        
        ((TextView)findViewById(R.id.userBio)).setText((user.biography.length() > 0) 
                                                       ? Html.fromHtml(user.biography)
                                                       : getString(R.string.no_biography_supplied));
        
        // user portrait
        final ImageView uploaderPortrait = (ImageView)findViewById(R.id.userPortrait);
        imageLoader.displayImage(user.mediumPortraitUrl, uploaderPortrait);        
        
        super.onItemReceived(user);
       
    }    

    @Override
    protected SectionedActionsAdapter fillWithActions(SectionedActionsAdapter actionsAdapter, UserInfo user) {
        
        // TODO: is staff, is plus
        
        // Statistics section
        int statsSection = actionsAdapter.addSection(getString(R.string.statistics));
        // number of videos
        final ActionItem videoAction = actionsAdapter.addAction(statsSection, R.drawable.video, 
                                 Utils.format(getString(R.string.num_of_videos), "num", String.valueOf(user.videosUploaded)));
        if (user.videosUploaded > 0) {
            videoAction.onClick =  new OnClickListener() {
                @Override public void onClick(View v) { Dialogs.makeToast(UserActivity.this, getString(R.string.currently_not_supported)); };
            };
        }
        // number of albums
        final ActionItem albumAction = actionsAdapter.addAction(statsSection, R.drawable.album, 
                                 Utils.format(getString(R.string.num_of_albums), "num", String.valueOf(user.albumsCount)));
        if (user.albumsCount > 0) {
            albumAction.onClick =  new OnClickListener() {
                @Override public void onClick(View v) { Dialogs.makeToast(UserActivity.this, getString(R.string.currently_not_supported)); };
            };
        }
        // number of channels
        final ActionItem channelAction = actionsAdapter.addAction(statsSection, R.drawable.channel, 
                Utils.format(getString(R.string.num_of_channels), "num", String.valueOf(user.channelsCount)));
        if (user.channelsCount > 0) {
            channelAction.onClick =  new OnClickListener() {
                @Override public void onClick(View v) { Dialogs.makeToast(UserActivity.this, getString(R.string.currently_not_supported)); };
            };
        }
        // number of contacts
        final ActionItem contactAction = actionsAdapter.addAction(statsSection, R.drawable.contact, 
                Utils.format(getString(R.string.num_of_contacts), "num", String.valueOf(user.contactsCount)));
        if (user.contactsCount > 0) {
            contactAction.onClick =  new OnClickListener() {
                @Override public void onClick(View v) { Dialogs.makeToast(UserActivity.this, getString(R.string.currently_not_supported)); };
            };
        }
        // number of appearances
        final ActionItem appearanceAction = actionsAdapter.addAction(statsSection, R.drawable.appearance, 
                Utils.format(getString(R.string.num_of_appearances), "num", String.valueOf(user.videosAppearsIn)));
        if (user.videosAppearsIn > 0) {
            appearanceAction.onClick =  new OnClickListener() {
                @Override public void onClick(View v) { Dialogs.makeToast(UserActivity.this, getString(R.string.currently_not_supported)); };
            };
        }
        // number of likes
        final ActionItem likesAction = actionsAdapter.addAction(statsSection, R.drawable.like, 
                Utils.format(getString(R.string.num_of_likes), "num", String.valueOf(user.videosLiked)));
        if (user.videosLiked > 0) {
            likesAction.onClick =  new OnClickListener() {
                @Override public void onClick(View v) { Dialogs.makeToast(UserActivity.this, getString(R.string.currently_not_supported)); };
            };
        }
        
        // Information section
        int infoSection = actionsAdapter.addSection(getString(R.string.information));
        // location
        actionsAdapter.addAction(infoSection, R.drawable.location,
                                 Utils.format(getString(R.string.location_is), "place", user.location));
        // created on
        actionsAdapter.addAction(infoSection, R.drawable.duration,
                                 Utils.format(getString(R.string.created_on), "time", user.createdOn));
        // from staff
        if (user.fromStaff) {
            actionsAdapter.addAction(infoSection, R.drawable.staff, getString(R.string.user_from_staff));    
        }
        // plus member
        if (user.isPlusMember) {
            actionsAdapter.addAction(infoSection, R.drawable.plus, getString(R.string.user_is_plus_member));    
        }        
        
        return actionsAdapter;
    }

}