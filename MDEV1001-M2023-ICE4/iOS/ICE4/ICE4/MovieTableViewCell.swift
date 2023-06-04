//
//  MovieTableViewCell.swift
//  ICE4
//
//  Created by Abhijit Singh on 03/06/23.
//

import UIKit

final class MovieTableViewCell: UITableViewCell {
    
    static var reuseId = String(describing: MovieTableViewCell.self)
    
    private lazy var titleLabel: UILabel = {
        return getLabel(with: .systemFont(ofSize: 18))
    }()
    
    private lazy var ratingLabel: UILabel = {
        return getLabel(with: .systemFont(ofSize: 15))
    }()
    
    private lazy var studioLabel: UILabel = {
        return getLabel(with: .systemFont(ofSize: 14))
    }()
    
    private lazy var descriptionLabel: UILabel = {
        return getLabel(with: .systemFont(ofSize: 12))
    }()

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: .subtitle, reuseIdentifier: reuseIdentifier)
        setup()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}

// MARK: - Exposed Helpers
extension MovieTableViewCell {
    
    func configure(with movie: Movie) {
        titleLabel.text = movie.title
        ratingLabel.text = "Rated: \(movie.critics_rating)"
        studioLabel.text = movie.studio
        descriptionLabel.text = movie.short_description
    }
    
}

// MARK: - Private Helpers
private extension MovieTableViewCell {
    
    func setup() {
        addTitleLabel()
        addRatingLabel()
        addStudioLabel()
        addDescriptionLabel()
    }
    
    func addTitleLabel() {
        contentView.addSubview(titleLabel)
        titleLabel.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 10).isActive = true
        titleLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 20).isActive = true
        titleLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -120).isActive = true
    }
    
    func addRatingLabel() {
        contentView.addSubview(ratingLabel)
        ratingLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -20).isActive = true
        ratingLabel.centerYAnchor.constraint(equalTo: titleLabel.centerYAnchor).isActive = true
    }
    
    func addStudioLabel() {
        contentView.addSubview(studioLabel)
        studioLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 5).isActive = true
        studioLabel.leadingAnchor.constraint(equalTo: titleLabel.leadingAnchor).isActive = true
        studioLabel.trailingAnchor.constraint(equalTo: ratingLabel.trailingAnchor).isActive = true
    }
    
    func addDescriptionLabel() {
        contentView.addSubview(descriptionLabel)
        descriptionLabel.topAnchor.constraint(equalTo: studioLabel.bottomAnchor, constant: 8).isActive = true
        descriptionLabel.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: -10).isActive = true
        descriptionLabel.leadingAnchor.constraint(equalTo: titleLabel.leadingAnchor).isActive = true
        descriptionLabel.trailingAnchor.constraint(equalTo: ratingLabel.trailingAnchor).isActive = true
    }
    
    func getLabel(with font: UIFont) -> UILabel {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = font
        label.numberOfLines = .zero
        return label
    }
    
}
