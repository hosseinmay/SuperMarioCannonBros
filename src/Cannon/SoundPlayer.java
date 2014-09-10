package Cannon;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

// Plays sounds passed to it
public class SoundPlayer {

    static File filSound;
    static boolean isFileThere, isLoop=false;

    public void loop() {
        isLoop=true;
        if (isFileThere) {
            playSoundFile(filSound);
        }
    }
    public static void loop(File file) {
        isLoop=true;
        playSoundFile(file);
    }
    public static void loop(String sFile) {
        isLoop=true;
        playSoundFile(sFile);
    }
    public void play() {
        if (isFileThere) {
            playSoundFile(filSound);
        }
    }

    public void play(File file) {

        playSoundFile(file);

    }

    public static void playSoundFile(String sFile) {
        playSoundFile(new File(sFile));
    }

    public static void playSoundFile(final File file) {//http://java.ittoolbox.com/groups/technical-functional/java-l/sound-in-an-application-90681
        new Thread(//http://stackoverflow.com/questions/4708254/how-to-play-audio-in-java-application
                new Runnable() {

            public void run() {

                try {
//get an AudioInputStream
                    AudioInputStream ais = AudioSystem.getAudioInputStream(file);
//get the AudioFormat for the AudioInputStream
                    AudioFormat audioformat = ais.getFormat();

//ULAW format to PCM format conversion
                    if ((audioformat.getEncoding() == AudioFormat.Encoding.ULAW)
                            || (audioformat.getEncoding() == AudioFormat.Encoding.ALAW)) {
                        AudioFormat newformat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                audioformat.getSampleRate(),
                                audioformat.getSampleSizeInBits() * 2,
                                audioformat.getChannels(),
                                audioformat.getFrameSize() * 2,
                                audioformat.getFrameRate(), true);
                        ais = AudioSystem.getAudioInputStream(newformat, ais);
                        audioformat = newformat;
                    }

//checking for a supported output line
                    DataLine.Info datalineinfo = new DataLine.Info(SourceDataLine.class, audioformat);
                    if (!AudioSystem.isLineSupported(datalineinfo)) {
                        System.out.println("Line matching " + datalineinfo + " is not supported.");
                    } else {
                        System.out.println("Line matching " + datalineinfo + " is supported.");
//opening the sound output line
                        SourceDataLine sourcedataline = (SourceDataLine) AudioSystem.getLine(datalineinfo);
                        sourcedataline.open(audioformat);
                        sourcedataline.start();
//Copy data from the input stream to the output data line
                        int framesizeinbytes = audioformat.getFrameSize();
                        int bufferlengthinframes = sourcedataline.getBufferSize() / 8;
                        int bufferlengthinbytes = bufferlengthinframes * framesizeinbytes;
                        byte[] sounddata = new byte[bufferlengthinbytes];
                        int numberofbytesread = 0;
                        while ((numberofbytesread = ais.read(sounddata)) != -1) {
                            int numberofbytesremaining = numberofbytesread;
                            sourcedataline.write(sounddata, 0, numberofbytesread);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    if(isLoop){
                        playSoundFile(file);
                    }
                }
            }
        }).start();
    }

    public void stop() {
        throw new UnsupportedOperationException("Create something first... DUH");//http://stackoverflow.com/questions/2205565/java-clean-way-to-automatically-throw-unsupportedoperationexception-when-calling

    }

    public void setSoundFile(File file) {
        isFileThere = true;
        filSound = file;
    }

    public void setSoundFile(String sFile) {
        isFileThere = true;
        filSound = new File(sFile);
    }
}
