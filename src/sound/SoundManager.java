package sound;

import java.util.*;

import gui.*;
import one.*;
import player.*;

public class SoundManager {

  private Optional<PlayerLocation> playerLocationOpt;
  private Map<String, Sound> soundFiles;

  private ArrayList<SoundArea> sounds;

  private Sound currentsound;

  public SoundManager() {
    sounds = new ArrayList<>();
    soundFiles = new HashMap<>();
  }

  private void changeSound(Sound sound, boolean fadeIn) {
    if (currentsound != sound) {
      if (currentsound != null) {
        currentsound.fadeOut(.15);
      }
      if (sound != null) {
        if( fadeIn ) {
          sound.fadeIn(.05);
        }
        else {
          sound.play(0);
        }
      }
      currentsound = sound;
      if (currentsound != null) {
        System.out.println("currentsound is now " + currentsound.getUrl());
      }
    }
  }

  public void playMusic() {
    if( World.playmusic ) {
      playerLocationOpt.ifPresent(playerLocation -> {
        for (SoundArea s : sounds) {
          if (s.in(playerLocation.getPlayerX(), playerLocation.getPlayerY())) {
            changeSound(s.getSound(), true);
          }
        }
      });
    }
  }

  public void stopMusic() {
    if (currentsound != null) {
      currentsound.fadeOut(10);
      currentsound.stopplaying();
      currentsound = null;
    }
  }
  public void fadeOutMusic() {
    if (currentsound != null) {
      currentsound.fadeOut(.15);
      currentsound.stopplaying();
      currentsound = null;
    }
  }

  public void playDeathMusic() {
    if( World.playmusic ) {
      Sound deathSound = soundFiles.get("death.wav");
      if( deathSound != null ) {
        changeSound(deathSound, false);
      }
    }
  }
  public void playMenuMusic() {
    if (World.playmusic) {
      Sound menuSound = soundFiles.get("mainmenu.wav");
      if (menuSound != null) {
        changeSound(menuSound, false);
//        menuSound.play(-5);
      }
    }
  }

  public void addPlayerLocation(PlayerLocation loc) {
    playerLocationOpt = Optional.of(loc);
  }

  public void loadResources() {
    Frame.print("Loading World Music: ");
    initSound("death.wav", -2, false);
    initSound("grass.wav", -15, true);
    initSound("combat1.wav", -15, true);
    initSound("training.wav", -7, false);
    initSound("stronghold.wav", -16, true);
    initSound("combat4.wav", -5, false);
    initSound("combat3.wav", -5, true);
    initSound("lava.wav", -5, true);
    initSound("necro.wav", -3, true);
    initSound("empty.wav", -5, true);
    initSound("mainmenu.wav", -10, true);
    Frame.println();
  }

  public boolean addSoundArea(String fileName, int minimumX, int maximumX, int minimumY, int maximumY) {
    Sound sound = soundFiles.get(fileName);
    if (sound == null) {
      return false;
    }
    sounds.add(new SoundArea(sound, minimumX, maximumX, minimumY, maximumY));
    return true;
  }

  private void initSound(final String url, float volume, boolean loop) {
    Frame.print(url + ", ");
    Sound bob = new Sound(url, volume, loop);
    bob.start();
    soundFiles.put(url, bob);
  }
}
