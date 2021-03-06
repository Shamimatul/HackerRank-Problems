import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution{

	public static class Pump
	{
		int petrol;
		int distance;
		int extra;
		public Pump(int petrol, int distance) {
			// TODO Auto-generated constructor stub
			this.petrol = petrol;
			this.distance = distance;
			extra = petrol - distance;
		}
	}

	public static void main(String[] args)
	{
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        String string;
        String[] str;
        Pump[] pumpArray = new Pump[n];
        for (int i = 0; i < n; i++)
        {
			string = in.nextLine();
			str = string.split(" ");
			int p = Integer.parseInt(str[0]);
			int d = Integer.parseInt(str[1]);
			pumpArray[i] = new Pump(p, d);
		}

        Queue<Pump> queue = new LinkedList<Solution.Pump>();
        int index = 1;
        int current = 0;
        queue.add(pumpArray[0]);
        int remaining = pumpArray[0].extra;
        while(queue.size() != n)
        {
        	Pump pump;
        	if(remaining < 0)
        	{
        		pump = queue.remove();
        		remaining -= pump.extra;
        		current++;
        		current %= n;
        	}
        	else
        	{
        		pump = pumpArray[index++];
        		remaining += pump.extra;
        		queue.add(pump);
        		index %= n;

        	}
        }
        System.out.println(current);
	}

}
