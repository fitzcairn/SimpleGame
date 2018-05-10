package com.stevezero.apps.simplegame.android.services.impl;

import com.google.android.gms.games.Games;
import com.stevezero.apps.simplegame.android.R;
import com.stevezero.apps.simplegame.android.util.BaseGameActivity;
import com.stevezero.apps.simplegame.android.util.GameHelper;
import com.stevezero.game.external.Leaderboards;

/**
 * Implementation of leaderboards for android.
 */
public class AppLeaderboards extends Leaderboards {
  private final BaseGameActivity gameActivity;
  private final GameHelper gameHelper;

  public AppLeaderboards(BaseGameActivity gameActivity) {
    this.gameActivity = gameActivity;
    this.gameHelper = gameActivity.getGameHelper();

  }

  @Override
  public void showLeaderboards() {
    if (gameHelper.isSignedIn()) {
      gameActivity.startActivityForResult(
          Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getId()), 0);
    }
  }

  @Override
  public void submitScoreTo(String id, long score) {
    if (gameHelper.isSignedIn()) {
      Games.Leaderboards.submitScore(gameHelper.getApiClient(), id, score);
    }
  }

  @Override
  public void submitScore(long score) {
    submitScoreTo(getId(), score);
  }

  private String getId() {
    return gameActivity.getString(R.string.leaderboard_come_get_some);
  }
}
