<?php
// Handle POST request for forgotten password
$data = json_decode(file_get_contents('php://input'), true);

if (!isset($data['email'])) {
    echo json_encode(['status' => 'error', 'message' => 'Email is required.']);
    exit;
}

// Load existing users from the JSON file
$users = json_decode(file_get_contents('users.json'), true);

foreach ($users as $user) {
    if ($user['email'] == $data['email']) {
        // Simulate sending reset instructions via email
        echo json_encode(['status' => 'success', 'message' => 'Password reset instructions sent.']);
        exit;
    }
}

echo json_encode(['status' => 'error', 'message' => 'Email not found.']);
