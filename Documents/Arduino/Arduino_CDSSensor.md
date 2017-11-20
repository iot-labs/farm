## 조도센서 (uno)
 * 빛센서를 통해 빛의 양을 측정합니다.

### code 

<pre><code>
int lightPin = 0;
int threshold = 250;
void setup()
{

    Serial.begin(9600);
    pinMode(13, OUTPUT);
}
 
void loop()
{
    Serial.println(analogRead(lightPin)); 
    if(analogRead(lightPin) > threshold ){    
        digitalWrite(13, HIGH);
        Serial.println("high"); 
    }else{
        digitalWrite(13, LOW);
        Serial.println("low"); 
    }
    delay(1000);
}
</code></pre>

### 회로도
![image](https://user-images.githubusercontent.com/22341443/32991265-8140e5d0-cd7b-11e7-9951-71d48d8dfcc2.png)


