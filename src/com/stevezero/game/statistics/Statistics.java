package com.stevezero.game.statistics;

import com.stevezero.game.external.Achievements;
import com.stevezero.game.external.Leaderboards;
import com.stevezero.game.external.services.ServiceManager;

/**
 * Statistics accumulation for gameplay.  Persists for entire session.
 * 
 * TODO(stevemar): Update with more data fields.
 * 
 * This class is intended to be overridden by platform-specific instantiations
 * to enable platform features like achievements, leaderboards, etc.
 */
public class Statistics {
  private final Achievements achievementsService;
  private final Leaderboards leaderboardsService;
  private final long[] statistics = new long[Stat.NUM_STATS];

  private long highScoreSoFar = 0;
  
  public Statistics(ServiceManager serviceManager) {
    this.achievementsService = serviceManager.getAchievementsService();
    this.leaderboardsService = serviceManager.getLeaderboardsService();
  }

  /**
   * Add to a specific stat.
   */
  public Statistics addToStat(int stat, long increment) {
    assert(stat > 0 && stat < Stat.NUM_STATS);
    statistics[stat] += increment;
    onChange(stat);
    return this;
  }
  
  /**
   * Increment a specific stat.
   */
  public Statistics increment(int stat) {
    return addToStat(stat, 1);
  }
  
  /**
   * Get the value of a specific stat.
   */
  public long getStat(int stat) {
    assert(stat > 0 && stat < Stat.NUM_STATS);
    return statistics[stat];
  }
  
  /**
   * Add to the score (shortcut).
   */
  public Statistics addScore(long score) {
    return addToStat(Stat.SCORE, score);
  }

  /**
   * Get the current score (shortcut).
   */
  public long getScore() {
    return statistics[Stat.SCORE];
  }
  
  /**
   * Clear all statistics.
   */
  public void clear() {
    for(int i = 0; i < statistics.length; i++) {
      statistics[i] = 0;
    }
  }

  // Do we need to react to a stat change?
  private void onChange(int stat) {
    long statValue = getStat(stat);
    switch(stat) {
      case(Stat.SCORE):
        maybeUpdateLeaderboard();
        break;
      case(Stat.BANANA):
        // Achievement for 3 bananas
        if (statValue == 3) {
          achievementsService.unlock(achievementsService.getIdFor(Achievements.LETS_GO_BANANAS));
        }
        break;
      case(Stat.CHERRY):
        // Achievement for 5 cherries.
        if (statValue == 5) {
          achievementsService.unlock(achievementsService.getIdFor(Achievements.CHERRY_PIE));
        }
        break;
      case(Stat.ELDERBERRY):
        // Achievement for 4 elderberries.
        if (statValue == 4) {
          achievementsService.unlock(achievementsService.getIdFor(Achievements.ELDERBERRY_SUPREME));
        }
        break;
      case(Stat.DATE):
        // Achievement for 2 dates.
        if (statValue == 2) {
          achievementsService.unlock(
              achievementsService.getIdFor(Achievements.ITS_NOT_A_TURD__HONEST));
        }
        break;
      case(Stat.DODGE):
        // Achievement for dodging once.
        if (statValue == 1) {
          achievementsService.unlock(
              achievementsService.getIdFor(Achievements.THRILLSON__HOW_ABOUT_TWINKLETOES));
        }
        break;
      case(Stat.BURRITO):
        // Achievement for 100 burritos.
        if (statValue == 100) {
          achievementsService.unlock(achievementsService.getIdFor(Achievements.BURRITO_GOD));
        }
        break;
      case(Stat.DOGFOOD):
        // Achievement for winning once.
        if (statValue == 1) {
          achievementsService.unlock(
              achievementsService.getIdFor(Achievements.GMS_CORE_AVAILABLE_FOR_DOGFOOD));
        }
        break;
      case(Stat.JARLSBERG):
        maybeUpdateAchievement();
        break;
      case(Stat.KENAFA):
        maybeUpdateAchievement();
        break;
      case(Stat.LONGHORN):
        maybeUpdateAchievement();
        break;
      case(Stat.MANCHEGO):
        maybeUpdateAchievement();
        break;
      default:
        break;
    }
  }
  
  // Multi-stat achievement.
  private void maybeUpdateAchievement() {
    if (getStat(Stat.JARLSBERG) == 1 &&
        getStat(Stat.KENAFA) == 1 &&
        getStat(Stat.LONGHORN) == 1 &&
        getStat(Stat.MANCHEGO) == 1) {
      achievementsService.unlock(
          achievementsService.getIdFor(Achievements.THRILLSON_ANGRY__THRILLSON_SMASH_GMS_CORE));
    }
  }
  
  private void maybeUpdateLeaderboard() {
    long score = getScore();
    if (score > highScoreSoFar) {
      highScoreSoFar = score;
      leaderboardsService.submitScore(score);
    }
  } 

}
