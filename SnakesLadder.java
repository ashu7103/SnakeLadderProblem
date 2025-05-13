import java.io.*;
import java.util.*;
public class SnakesLadder { // extends AbstractSnakeLadders {

    int N, M;
    int snakes[];
    int ladders[];
    int stepStart[];
    int stepEnd[];
    ArrayList<ArrayList<Integer>> revsnakes;
    ArrayList<ArrayList<Integer>> revladders;
    ArrayList<Integer> lad;
    int Optim;
    int negOptim;
    public SnakesLadder(String name) throws Exception {
        File file = new File(name);
        BufferedReader br = new BufferedReader(new FileReader(file));
        N = Integer.parseInt(br.readLine());

        M = Integer.parseInt(br.readLine());

        snakes = new int[N];
        ladders = new int[N];
        revladders = new ArrayList<ArrayList<Integer>>();
        revsnakes = new ArrayList<ArrayList<Integer>>();
        lad = new ArrayList<Integer>();

        for (int i = 0; i < N; i++) {
            snakes[i] = -1;
            ladders[i] = -1;
            revladders.add(new ArrayList<Integer>());
            revsnakes.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < M; i++) {
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

            if (source < destination) {
                ladders[source] = destination;
                revladders.get(destination).add(source);
                lad.add(source);
            } else {
                snakes[source] = destination;
                revsnakes.get(destination).add(source);
            }
        }
        br.close();
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(0);
        Optim = -1;
        stepStart = new int[N];
        stepEnd = new int[N];
        // System.out.println(q.isEmpty());
        while (!q.isEmpty()) {
            // System.out.println("here");
            int d = q.remove();
            // System.out.println(q.isEmpty());
            int ste;
            if (d == 0) {
                ste = 0;
            } else {
                ste = stepStart[d];
            }
            //	System.out.println(d);
            for (int i = 1; i <= 6; i++) {
                int temp = d + i;
                if (temp >= N) {
                    if (Optim == -1) {
                        Optim = ste + 1;
                    }
                    break;
                }
                //	System.out.println("Here");
                ArrayList<ArrayList<Integer>> arrgh = new ArrayList<ArrayList<Integer>>();
                while (snakes[temp] != -1 || ladders[temp] != -1) {
                    if (stepStart[temp] != 0) {
                        break;
                    }

                    stepStart[temp] = ste + 1;
                    ArrayList<Integer> tempo = new ArrayList<Integer>();
                    tempo.add(temp);
                    if (snakes[temp] != -1) {
                        tempo.add(0);
                        temp = snakes[temp];
                    } else {
                        tempo.add(1);
                        temp = ladders[temp];
                    }
                    arrgh.add(tempo);
                }
                if (stepStart[temp] == 0) {
                    stepStart[temp] = ste + 1;
                    q.add(temp);
                    for (int l = 0; l < arrgh.size(); l++) {
                        if (arrgh.get(l).get(1) == 0) {
                            snakes[arrgh.get(l).get(0)] = temp;
                        } else {
                            ladders[arrgh.get(l).get(0)] = temp;
                        }
                    }
                } else {
                    if (snakes[temp] != -1) {
                        temp = snakes[temp];
                    } else if (ladders[temp] != -1) {
                        temp = ladders[temp];
                    }
                    for (int l = 0; l < arrgh.size(); l++) {
                        if (arrgh.get(l).get(1) == 0) {
                            snakes[arrgh.get(l).get(0)] = temp;
                        } else {
                            ladders[arrgh.get(l).get(0)] = temp;
                        }
                    }
                }
            }
        }

        q.add(N);
        while (!q.isEmpty()) {
            int d = q.remove();
            int ste;
            if (d == N) {
                ste = 0;
            } else {
                ste = stepEnd[d];
            }
            for (int i = 1; i <= 6; i++) {
                int temp = d - i;
                if (temp <= 0) {
                    negOptim = ste + 1;
                    break;
                }

                Queue<Integer> qq = new LinkedList<Integer>();
                if (stepEnd[temp] == 0) {
                    qq.add(temp);
                }
                while (!qq.isEmpty()) {
                    int p = qq.remove();
                    //	System.out.println(p+","+revladders.get(p));
                    if (stepEnd[p] != 0) {
                        break;
                    }

                    stepEnd[p] = ste + 1;
                    //	System.out.println(stepEnd[p]);
                    boolean xs = false;
                    if (!revsnakes.get(p).isEmpty()) {
                        for (int a = 0; a < revsnakes.get(p).size(); a++) {
                            if (stepEnd[revsnakes.get(p).get(a)] != 0) {
                                stepEnd[revsnakes.get(p).get(a)] = stepEnd[p];
                            }
                            qq.add(revsnakes.get(p).get(a));
                        }
                        xs = true;
                    }
                    if (!revladders.get(p).isEmpty()) {
                        for (int a = 0; a < revladders.get(p).size(); a++) {
                            qq.add(revladders.get(p).get(a));
                        }
                        xs = true;
                    }
                    if (xs == false) {
                        q.add(p);
                    }
                }
            }
        }
    }

    public int OptimalMoves() {
        if (Optim == -1) {
            return -1;
        }

        return Optim;
    }

    public int Query(int x, int y) {
		// updating end point of ladder/snake
        if (snakes[y] != -1) {
            y = snakes[y];
        } else if (ladders[y] != -1) {
            y = ladders[y];
        }
        // System.out.println(stepStart[x]+","+stepEnd[y]);
        if (x > y) {
            if (Optim > stepStart[x] + stepEnd[y]) {
				System.out.print(Optim + " to "+ (stepStart[x] + stepEnd[y]));
                return 1;
            }
        } else {
            if (Optim > stepStart[x] + stepEnd[y]) {
				System.out.print(Optim + " to "+ (stepStart[x] + stepEnd[y]));
                return 1;
            }
        }
        return -1;
    }
    private int BinarySearch(ArrayList<Integer> arr, int key) {
        int start = 0;
        int end = arr.size() - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (arr.get(mid) >= key) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return end;
    }

    public int[] FindBestNewSnake() {
        int result[] = {-1, -1};
        int least = Optim;
        Collections.sort(lad);
        // System.out.println(lad);
        int arr[][] = new int[lad.size()][2];
        if (lad.size() != 0) {
            // System.out.println(ladders[lad.get(0)]);
            arr[0][0] = stepEnd[ladders[lad.get(0)]];
            arr[0][1] = 0;
        }
        for (int i = 1; i < lad.size(); i++) {
            // System.out.println(ladders[lad.get(i)]);
            int f = stepEnd[ladders[lad.get(i)]];
            if (arr[i - 1][0] <= f) {
                arr[i] = arr[i - 1];
            } else {
                arr[i][0] = f;
                arr[i][1] = i;
            }
        }
        // for (int i=0;i<lad.size();i++){
        // System.out.println(arr[i][0]+","+arr[i][1]);
        // }
        for (int i = 0; i < lad.size(); i++) {
            int j = BinarySearch(lad, ladders[lad.get(i)]);
            if (least > stepStart[lad.get(i)] + arr[j][0]) {
                least = stepStart[lad.get(i)] + arr[j][0];
                result[0] = ladders[lad.get(i)];
                result[1] = lad.get(arr[j][1]);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            SnakesLadder sn = new SnakesLadder("input.txt");
            System.out.println("OPTIMAL Move : Expected: 6,  Actual: "+sn.OptimalMoves());
            System.out.println("  - Query Result : "+sn.Query(54, 50));
            System.out.println("  - Query Result : "+sn.Query(54, 95));
            System.out.println("  - Query Result : "+sn.Query(54, 10));

            int arr[] = sn.FindBestNewSnake();
            System.out.println(" BestNewSnake : "+arr[0] + " to " + arr[1]);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}