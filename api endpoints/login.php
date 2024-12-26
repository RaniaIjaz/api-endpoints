<?php
// Handle POST request to login a user
$data = json_decode(file_get_contents('php://input'), true);

if (!isset($data['email'], $data['password'])) {
    echo json_encode(['status' => 'error', 'message' => 'Email and password are required.']);
    exit;
}

// Load existing users from the JSON file
$users = json_decode(file_get_contents('users.json'), true);

foreach ($users as $user) {
    if ($user['email'] == $data['email'] && password_verify($data['password'], $user['password'])) {
        // Generate a new auth token
        $token = bin2hex(random_bytes(16));

        // Update the user's auth token
        $user['auth_token'] = $token;

        // Save the updated users array to the JSON file
        file_put_contents('users.json', json_encode($users));

        echo json_encode(['status' => 'success', 'auth_token' => $token]);
        exit;
    }
}

echo json_encode(['status' => 'error', 'message' => 'Invalid email or password.']);
