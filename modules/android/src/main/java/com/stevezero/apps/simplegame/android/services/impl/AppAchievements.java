package com.stevezero.apps.simplegame.android.services.impl;

import com.google.android.gms.games.Games;
import com.stevezero.apps.simplegame.android.R;
import com.stevezero.apps.simplegame.android.util.BaseGameActivity;
import com.stevezero.apps.simplegame.android.util.GameHelper;
import com.stevezero.game.external.Achievements;

/**
 * Implementation of achievements for android.
 */
public class AppAchievements extends Achievements {
  private final BaseGameActivity gameActivity;
  private final GameHelper gameHelper;

  public AppAchievements(BaseGameActivity gameActivity) {
    this.gameActivity = gameActivity;
    this.gameHelper = gameActivity.getGameHelper();
  }

  @Override
  public void showAchievements() {
    if (gameHelper.isSignedIn()) {
      gameActivity.startActivityForResult(
          Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 0);
    }
  }

  @Override
  public void unlock(String id) {
    if (gameHelper.isSignedIn()) {
      Games.Achievements.unlock(gameHelper.getApiClient(), id);
    }
  }

  @Override
  public void increment(String id, int numSteps) {
    if (gameHelper.isSignedIn()) {
      Games.Achievements.increment(gameHelper.getApiClient(), id, numSteps);
    }
  }

  @Override
  public String getIdFor(int achievementId) {
    switch(achievementId) {
      case(Achievements.LETS_GO_BANANAS):
        return gameActivity.getString(R.string.achievement_lets_go_bananas);
      case(Achievements.BURRITO_GOD):
        return gameActivity.getString(R.string.achievement_burrito_god);
      case(Achievements.THRILLSON_ANGRY__THRILLSON_SMASH_GMS_CORE):
        return gameActivity.getString(R.string.achievement_thrillson_angry__thrillson_smash_gms_core);
      case(Achievements.THRILLSON__HOW_ABOUT_TWINKLETOES):
        return gameActivity.getString(R.string.achievement_thrillson__how_about_twinkletoes);
      case(Achievements.CHERRY_PIE):
        return gameActivity.getString(R.string.achievement_cherry_pie);
      case(Achievements.ITS_NOT_A_TURD__HONEST):
        return gameActivity.getString(R.string.achievement_its_not_a_turd__honest);
      case(Achievements.GMS_CORE_AVAILABLE_FOR_DOGFOOD):
        return gameActivity.getString(R.string.achievement_gms_core_available_for_dogfood);
      case(Achievements.ELDERBERRY_SUPREME):
        return gameActivity.getString(R.string.achievement_elderberry_supreme);
      default:
        throw new UnsupportedOperationException("No achievement id for " + achievementId);
    }
  }
}
