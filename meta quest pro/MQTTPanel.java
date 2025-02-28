package javiergs;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class MQTTPanel extends JPanel {
	
	// 3D boundaries (adjust based on your scene)
	private static final float MIN_X = -1.5f;
	private static final float MAX_X = 1.5f;
	private static final float MIN_Y = 0.0f;
	private static final float MAX_Y = 2.0f;
	
	// Positions for hands and object (default values)
	private float cubeX = 0.0f, cubeY = 0.5f;
	
	// ✅ Head and torso
	private float headX = 0.0f, headY = 1.8f, headZ = 0.0f;
	private float torsoX = 0.0f, torsoY = 1.4f, torsoZ = 0.0f;
	private float hipsX = 0.0f, hipsY = 1.0f, hipsZ = 0.0f;  // Approximate waist level
	
	// ✅ Arms
	private float leftUpperArmX = -0.3f, leftUpperArmY = 1.4f, leftUpperArmZ = 0.0f;
	private float leftLowerArmX = -0.5f, leftLowerArmY = 1.2f, leftLowerArmZ = 0.0f;
	private float leftWristX = -0.6f, leftWristY = 1.1f, leftWristZ = 0.0f;
	private float rightUpperArmX = 0.3f, rightUpperArmY = 1.4f, rightUpperArmZ = 0.0f;
	private float rightLowerArmX = 0.5f, rightLowerArmY = 1.2f, rightLowerArmZ = 0.0f;
	private float rightWristX = 0.6f, rightWristY = 1.1f, rightWristZ = 0.0f;
	
	// ✅ Hands and feet
	private float leftHandX = -0.3f, leftHandY = 1.0f, leftHandZ = 0.5f;
	private float rightHandX = 0.3f, rightHandY = 1.0f, rightHandZ = 0.5f;
	private float leftFootX = -0.3f, leftFootY = 0.2f, leftFootZ = 0.5f;
	private float rightFootX = 0.3f, rightFootY = 0.2f, rightFootZ = 0.5f;
	
	// ✅ Corrected Eye Positions
	private float leftEyeX = -0.03f, leftEyeY = 1.6f, leftEyeZ = 0.0f;
	private float rightEyeX = 0.03f, rightEyeY = 1.6f, rightEyeZ = 0.0f;
	
	// ✅ Gaze Directions
	private float leftGazeX = 0.0f, leftGazeY = 0.0f, leftGazeZ = 1.0f;
	private float rightGazeX = 0.0f, rightGazeY = 0.0f, rightGazeZ = 1.0f;
	
	// ✅ Fixation point
	private float fixationX = 0.0f, fixationY = 1.6f, fixationZ = 2.0f;
	
	public MQTTPanel() {
		new Thread(() -> startMQTTSubscriber("tcp://test.mosquitto.org:1883", "jgs/unity/test")).start();
	}
	
	private void startMQTTSubscriber(String broker, String topic) {
		try {
			MqttClient client = new MqttClient(broker, MqttClient.generateClientId());
			client.connect();
			client.subscribe(topic, (t, message) -> {
				String payload = new String(message.getPayload());
				// ("Received: " + payload);
				parseData(payload);
				repaint();
			});
			// ("Subscribed to topic: " + topic);
		} catch (Exception e) {
			// ("MQTT error: " + e.getMessage());
		}
	}
	
	private void parseData(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			
			// ✅ Parse head and torso
			JSONObject head = obj.getJSONObject("head");
			headX = (float) head.getDouble("x");
			headY = (float) head.getDouble("y");
			headZ = (float) head.getDouble("z");
			
			JSONObject torso = obj.getJSONObject("torso");
			torsoX = (float) torso.getDouble("x");
			torsoY = (float) torso.getDouble("y");
			torsoZ = (float) torso.getDouble("z");
			
			JSONObject hip = obj.getJSONObject("hips");
			hipsX = (float) hip.getDouble("x");
			hipsY = (float) hip.getDouble("y");
			hipsZ = (float) hip.getDouble("z");
			
			// ✅ Parse hands and feet
			JSONObject leftHand = obj.getJSONObject("leftHand");
			leftHandX = (float) leftHand.getDouble("x");
			leftHandY = (float) leftHand.getDouble("y");
			JSONObject rightHand = obj.getJSONObject("rightHand");
			rightHandX = (float) rightHand.getDouble("x");
			rightHandY = (float) rightHand.getDouble("y");
			JSONObject leftFoot = obj.getJSONObject("leftFoot");
			leftFootX = (float) leftFoot.getDouble("x");
			leftFootY = (float) leftFoot.getDouble("y");
			JSONObject rightFoot = obj.getJSONObject("rightFoot");
			rightFootX = (float) rightFoot.getDouble("x");
			rightFootY = (float) rightFoot.getDouble("y");
			
			// ✅ arms
			JSONObject leftUpperArmObj = obj.getJSONObject("leftArmUp");
			leftUpperArmX = (float) leftUpperArmObj.getDouble("x");
			leftUpperArmY = (float) leftUpperArmObj.getDouble("y");
			JSONObject rightUpperArmObj = obj.getJSONObject("rightArmUp");
			rightUpperArmX = (float) rightUpperArmObj.getDouble("x");
			rightUpperArmY = (float) rightUpperArmObj.getDouble("y");
			JSONObject leftLowerArmObj = obj.getJSONObject("leftArmLow");
			leftLowerArmX = (float) leftLowerArmObj.getDouble("x");
			leftLowerArmY = (float) leftLowerArmObj.getDouble("y");
			JSONObject rightLowerArmObj = obj.getJSONObject("rightArmLow");
			rightLowerArmX = (float) rightLowerArmObj.getDouble("x");
			rightLowerArmY = (float) rightLowerArmObj.getDouble("y");
			
			// Parse left eye
			JSONObject leftEye = obj.getJSONObject("leftEye");
			leftEyeX = (float) leftEye.getDouble("x");
			leftEyeY = (float) leftEye.getDouble("y");
			leftEyeZ = (float) leftEye.getDouble("z");
			// Parse right eye
			JSONObject rightEye = obj.getJSONObject("rightEye");
			rightEyeX = (float) rightEye.getDouble("x");
			rightEyeY = (float) rightEye.getDouble("y");
			rightEyeZ = (float) rightEye.getDouble("z");
			// LeftGAZE
			JSONObject leftEyeGaze = obj.getJSONObject("leftEyeGaze");
			leftGazeX = (float) leftEyeGaze.getDouble("x");
			leftGazeY = (float) leftEyeGaze.getDouble("y");
			leftGazeZ = (float) leftEyeGaze.getDouble("z");
			// Right GAZE
			JSONObject rightEyeGaze = obj.getJSONObject("rightEyeGaze");
			rightGazeX = (float) rightEyeGaze.getDouble("x");
			rightGazeY = (float) rightEyeGaze.getDouble("y");
			rightGazeZ = (float) rightEyeGaze.getDouble("z");
			// ✅ Compute fixation point using both gaze rays
			float[] leftEyePos = {leftEyeX, leftEyeY, leftEyeZ};
			float[] leftGazeDir = {leftGazeX, leftGazeY, leftGazeZ};
			float[] rightEyePos = {rightEyeX, rightEyeY, rightEyeZ};
			float[] rightGazeDir = {rightGazeX, rightGazeY, rightGazeZ};
			float[] fixationPoint = computeFixationPoint(leftEyePos, leftGazeDir, rightEyePos, rightGazeDir);
			// ✅ Store fixation point
			fixationX = fixationPoint[0];
			fixationY = fixationPoint[1];
			fixationZ = fixationPoint[2];
			
			// Parse cube
			JSONObject cube = obj.getJSONObject("cube");
			cubeX = (float) cube.getDouble("x");
			cubeY = (float) cube.getDouble("y");
			//System.out.println("Cube: " + cubeX + ", " + cubeY);
			
		} catch (Exception e) {
			System.out.println("Error parsing JSON: " + e.getMessage());
		}
	}
	
	private int[] transformToScreen(float x, float y) {
		
		// Use the actual dimensions of the JPanel
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		// Transform coordinates dynamically based on current panel size
		int px = (int) ((x - MIN_X) / (MAX_X - MIN_X) * panelWidth);
		int py = (int) ((y - MIN_Y) / (MAX_Y - MIN_Y) * panelHeight);
		// Flip y-axis for screen coordinates
		py = panelHeight - py;
		return new int[]{px, py};
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		// Clear screen
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, panelWidth, panelHeight);
		
		// ✅ Draw Head
		int[] headPos = transformToScreen(headX, headY);
		g.setColor(Color.GRAY);
		g.fillOval(headPos[0] - 7, headPos[1] - 7, 14, 14);
		// ✅ Draw Torso
		int[] torsoPos = transformToScreen(torsoX, torsoY);
		g.setColor(Color.GREEN);
		g.fillOval(torsoPos[0] - 7, torsoPos[1] - 7, 14, 14);
		// ✅ Draw Hips (optional for leg connection)
		int[] hipsPos = transformToScreen(hipsX, hipsY);
		g.setColor(Color.MAGENTA);
		g.fillOval(hipsPos[0] - 7, hipsPos[1] - 7, 14, 14);
		// ✅ Connect body parts with lines (skeleton)
		g.setColor(Color.WHITE);
		g.drawLine(headPos[0], headPos[1], torsoPos[0], torsoPos[1]); // Neck
		g.drawLine(torsoPos[0], torsoPos[1], hipsPos[0], hipsPos[1]); // Torso to hips

		
		
		// ✅ Draw Left Hand
		int[] leftHandPos = transformToScreen(leftHandX, leftHandY);
		g.setColor(Color.ORANGE);
		g.fillOval(leftHandPos[0] - 5, leftHandPos[1] - 5, 10, 10);
		
		// ✅ Draw Right Hand
		int[] rightHandPos = transformToScreen(rightHandX, rightHandY);
		g.setColor(Color.PINK);
		g.fillOval(rightHandPos[0] - 5, rightHandPos[1] - 5, 10, 10);
		
		// ✅ Draw Feet
		int[] leftFootPos = transformToScreen(leftFootX, leftFootY);
		int[] rightFootPos = transformToScreen(rightFootX, rightFootY);
		g.setColor(Color.CYAN);
		g.fillOval(leftFootPos[0] - 5, leftFootPos[1] - 5, 10, 10);
		g.fillOval(rightFootPos[0] - 5, rightFootPos[1] - 5, 10, 10);
		
		// ✅ Connect body parts with lines (skeleton)
		g.setColor(Color.WHITE);
		g.drawLine(headPos[0], headPos[1], torsoPos[0], torsoPos[1]); // Neck
		g.drawLine(torsoPos[0], torsoPos[1], leftHandPos[0], leftHandPos[1]); // Left arm
		g.drawLine(torsoPos[0], torsoPos[1], rightHandPos[0], rightHandPos[1]); // Right arm
		g.drawLine(hipsPos[0], hipsPos[1], leftFootPos[0], leftFootPos[1]); // Left leg
		g.drawLine(hipsPos[0], hipsPos[1], rightFootPos[0], rightFootPos[1]); // Right leg
		
		// Draw left Eye
		int[] leftEyePos = transformToScreen(leftEyeX, leftEyeY);
		g.setColor(Color.BLUE);
		g.fillOval(leftEyePos[0] - 5, leftEyePos[1] - 5, 10, 10);
		// Draw right Eye
		int[] rightEyePos = transformToScreen(rightEyeX, rightEyeY);
		g.setColor(Color.BLUE);
		g.fillOval(rightEyePos[0] - 5, rightEyePos[1] - 5, 10, 10);
		// Fixation point
		int gazeLength = 100;
		g.setColor(Color.RED);
		g.drawLine(leftEyePos[0], leftEyePos[1], leftEyePos[0] + (int) (leftGazeX * gazeLength), leftEyePos[1] - (int) (leftGazeY * gazeLength));
		g.drawLine(rightEyePos[0], rightEyePos[1], rightEyePos[0] + (int) (rightGazeX * gazeLength), rightEyePos[1] - (int) (rightGazeY * gazeLength));
		
		// Draw cube
		int[] cubePos = transformToScreen(cubeX, cubeY);
		g.setColor(Color.WHITE);
		g.fillRect(cubePos[0] - 5, cubePos[1] - 5, 10, 10);

	}
	
	
	private float[] computeFixationPoint(float[] leftEyePos, float[] leftGazeDir, float[] rightEyePos, float[] rightGazeDir) {
		float minDepth = 1.2f;  // Minimum fixation depth
		float maxDepth = 3.5f;  // Maximum reasonable depth
		
		// ✅ Compute the average gaze direction (normalized)
		float avgGazeX = (leftGazeDir[0] + rightGazeDir[0]) / 2;
		float avgGazeY = (leftGazeDir[1] + rightGazeDir[1]) / 2;
		float avgGazeZ = (leftGazeDir[2] + rightGazeDir[2]) / 2;
		
		// ✅ Normalize the gaze direction
		float magnitude = (float) Math.sqrt(avgGazeX * avgGazeX + avgGazeY * avgGazeY + avgGazeZ * avgGazeZ);
		if (magnitude == 0) return new float[]{0.0f, 0.0f, 0.0f};  // Prevent division by zero
		avgGazeX /= magnitude;
		avgGazeY /= magnitude;
		avgGazeZ /= magnitude;
		
		// ✅ Estimate depth dynamically based on gaze forwardness
		float gazeDepthFactor = minDepth + (avgGazeZ + 1.0f) * (maxDepth - minDepth) / 2.0f;
		
		// ✅ Compute eye midpoint (average eye height)
		float eyeMidX = (leftEyePos[0] + rightEyePos[0]) / 2;
		float eyeMidY = (leftEyePos[1] + rightEyePos[1]) / 2;  // This ensures fixation doesn't drop
		float eyeMidZ = (leftEyePos[2] + rightEyePos[2]) / 2;
		
		// ✅ Compute fixation point
		float fixationX = eyeMidX + avgGazeX * gazeDepthFactor;
		float fixationY = eyeMidY + avgGazeY * gazeDepthFactor;  // Ensure Y follows gaze
		float fixationZ = eyeMidZ + avgGazeZ * gazeDepthFactor;
		
		return new float[]{fixationX, fixationY, fixationZ};
	}
	
	// ✅ Dot product helper function
	private float dot(float[] v1, float[] v2) {
		return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
	}
}
