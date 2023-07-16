//
//  MovieTableViewCell.swift
//  ICE8
//
//  Created by Abhijit Singh on 07/07/23.
//

import UIKit

final class MovieTableViewCell: UITableViewCell {
    
    static let identifier = String(describing: MovieTableViewCell.self)

    @IBOutlet private weak var ratingContainerView: UIView!
    @IBOutlet private weak var ratingLabel: UILabel!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var studioLabel: UILabel!
    @IBOutlet private weak var descriptionLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setup()
    }
    
}

// MARK: - Exposed Helpers
extension MovieTableViewCell {
    
    func configure(with viewModel: MovieCellViewModelable) {
        ratingContainerView.backgroundColor = viewModel.state.color.withAlphaComponent(0.6)
        ratingContainerView.layer.borderColor = viewModel.state.color.cgColor
        ratingLabel.text = String(viewModel.movie.criticsRating)
        titleLabel.text = viewModel.movie.title
        studioLabel.text = viewModel.movie.studio
        let description = viewModel.movie.description
        descriptionLabel.text = description
        descriptionLabel.isHidden = description == nil || !viewModel.isExpanded
    }
    
}

// MARK: - Private Helpers
private extension MovieTableViewCell {
    
    func setup() {
        ratingContainerView.layer.cornerRadius = 12
        ratingContainerView.layer.borderWidth = 3
    }
    
}

// MARK: - MovieCellViewModel.RatingState Helpers
private extension MovieCellViewModel.RatingState {
    
    var color: UIColor {
        switch self {
        case .good:
            return .systemGreen
        case .okay:
            return .systemYellow
        case .meh:
            return .systemOrange
        case .bad:
            return .systemRed
        case .unknown:
            return .clear
        }
    }
    
}
