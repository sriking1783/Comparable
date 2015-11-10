// A client that uses the synthesizer package to replicate a plucked guitar string sound
package synthesizer;
import java.util.HashMap;
import java.util.Map;

public class GuitarHeroLite {
      public static void main(String[] args) {

          // create two guitar strings, for concert A and C
          /*double CONCERT_A = 440.0;
          double CONCERT_C = CONCERT_A * Math.pow(2, 3.0/12.0);
          synthesizer.GuitarString stringA = new synthesizer.GuitarString(CONCERT_A);
          synthesizer.GuitarString stringC = new synthesizer.GuitarString(CONCERT_C);*/
          String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
          HashMap<Character, synthesizer.GuitarString> concerts = new HashMap<Character, synthesizer.GuitarString>();
          for (int i = 0, n = keyboard.length(); i < n; i++) {
              char c = keyboard.charAt(i);
              double temp = (440 * Math.pow(2,((i - 24) / 12)));
              synthesizer.GuitarString string = new synthesizer.GuitarString(temp);
              concerts.put(c, string);
          }
          while (true) {

              // check if the user has typed a key; if so, process it
              if (StdDraw.hasNextKeyTyped()) {
                  char key = StdDraw.nextKeyTyped();
                  if(concerts.containsKey(key)){
                      synthesizer.GuitarString string = concerts.get(key);
                      string.pluck();
                    }
                  //if      (key == 'a') { stringA.pluck(); }
                  //else if (key == 'c') { stringC.pluck(); }
              }

              // compute the superposition of samples
              //double sample = stringA.sample() + stringC.sample();
              double sample = 0;
              for (Map.Entry<Character, synthesizer.GuitarString> entry : concerts.entrySet())
              {
                  synthesizer.GuitarString string =  entry.getValue();
                  sample += string.sample();
              }

              // play the sample on standard audio
              // note: this is just playing the double value YOUR GuitarString
              //       class is generating.
              StdAudio.play(sample);

              // advance the simulation of each guitar string by one step
              for (Map.Entry<Character, synthesizer.GuitarString> entry : concerts.entrySet())
              {
                  synthesizer.GuitarString string =  entry.getValue();
                  string.tic();
              }
              //stringA.tic();
              //stringC.tic();
          }
       }
  }
